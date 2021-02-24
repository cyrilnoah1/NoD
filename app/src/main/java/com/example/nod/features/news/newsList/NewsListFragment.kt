package com.example.nod.features.news.newsList


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nod.R
import com.example.nod.common.*
import com.example.nod.data.local.models.News
import com.example.nod.features.news.NewsActivity
import kotlinx.android.synthetic.main.fragment_news_list.*
import kotlinx.android.synthetic.main.partial_no_data_layout.*

/**
 * The fragment that displays the [News] information list to the user.
 *
 * The news information that is displayed to the user is always fetched from the local cache.
 * Here, when the information is fetched from the internet, the retrieved data is stored in the
 * local cache, and the updated information is fetched dynamically by listening to the changes in
 * the database.
 */
class NewsListFragment : Fragment() {

    private val newsListAdapter by lazy {
        NewsListAdapter { news ->
            if (news.contentUrl.isValidUrl()) {

                val args = Bundle()
                args.putString(BUND_CONTENT_URL, news.contentUrl)

                clRoot?.findNavController()?.navigate(
                    R.id.action_newsListFragment_to_newsDetailsActivity,
                    args
                )
            }
        }
    }
    private var newsActivity: NewsActivity? = null

    private val sources by lazy { arguments?.getString(ARG_SOURCES) }

    private val viewModel: NewsListViewModel by lazy {
        val factory = NewsListViewModel.Factory(requireActivity().application)
        ViewModelProviders.of(this@NewsListFragment, factory).get(NewsListViewModel::class.java)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is NewsActivity) newsActivity = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 1. Making sure that the news information is fetched only when there is no data available.
        // 2. We make sure that we make to call manually when the user explicitly asks for it.
        // 3. This check is helpful during a configuration change.
        viewModel.fetchNewsInformation(getQuery(), getQueryType())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Setting up the news list.
        setupNewsList()
        setupDataReload()
    }

    /**
     * Function to retrieve the news information list and populate the [rvNewsList] RecyclerView.
     */
    private fun setupNewsList() {
        sources?.let { plToolbar?.visible() } ?: run { plToolbar?.gone() }
        // Setting up the RecyclerView.
        rvNewsList?.run {
            layoutManager = LinearLayoutManager(requireContext())
//            addItemDecoration(DividerItemDecoration(requireContext(), LinearLayout.VERTICAL))
            adapter = newsListAdapter
        }

        // Updating the news list data when there is a change.
        viewModel.obsNewsList.observe(this@NewsListFragment, Observer {

            it?.let { list ->

                when (getQueryType()) {
                    QueryType.SOURCE -> {
                        // Checking whether the data has been loaded correctly.
                        if (list.none { data -> data.sourceId == sources } && newsActivity?.getNetworkState() == false) {
                            rvNewsList?.gone()
                            plNoData?.visible()
                        } else {
                            rvNewsList?.visible()
                            plNoData?.gone()
                        }

                        newsListAdapter.submitList(list.filter { data -> data.sourceId == sources })
                    }
                    QueryType.COUNTRY_CODE -> {
                        // Checking whether the data has been loaded correctly.
                        if (list.isEmpty() && newsActivity?.getNetworkState() == false) {
                            rvNewsList?.gone()
                            plNoData?.visible()
                        } else {
                            rvNewsList?.visible()
                            plNoData?.gone()
                        }

                        newsListAdapter.submitList(list)
                    }
                }
            }
        })

        // Observing for failure messages.
        viewModel.obsError.observe(this@NewsListFragment, Observer {
            it?.takeIf { err -> !err.isBlank() }
                ?.let { msg -> toast(msg, Toast.LENGTH_LONG) }
        })
    }

    /**
     * Function to setup [srlRefreshContent] swipe-to-refresh layout.
     */
    private fun setupDataReload() {
        // Refreshing the data when swipe-to-refresh is performed.
        srlRefreshContent?.setOnRefreshListener {
            fetchDataOnNetwork()
        }

        // Refreshing the data when retry button is clicked.
        btnNoDataRetry?.setOnClickListener {
            fetchDataOnNetwork()
        }

        viewModel.obsProgress.observe(this@NewsListFragment, Observer {
            it?.let { inProgress ->
                if (inProgress) {
                    srlRefreshContent?.inProgress()
                    btnNoDataRetry?.isEnabled = false
                } else {
                    srlRefreshContent.progressComplete()
                    btnNoDataRetry?.isEnabled = true
                }
            }
        })
    }

    /**
     * Fetching the data when there is internet connection.
     */
    private fun fetchDataOnNetwork() {
        // Making sure that the latest data is fetched only when
        // the internet connection is available.
        if (newsActivity?.getNetworkState() == true) {
            viewModel.fetchNewsInformation(getQuery(), getQueryType())
        } else {
            srlRefreshContent?.progressComplete()
            newsActivity?.networkSnack?.show()
        }
    }

    private fun getQuery() = sources ?: NewsListViewModel.US_COUNTRY_CODE

    private fun getQueryType() = if (sources != null) QueryType.SOURCE else QueryType.COUNTRY_CODE

    companion object {
        const val ARG_SOURCES = "sources"

        @JvmStatic
        fun newInstance() = NewsListFragment()
    }
}
