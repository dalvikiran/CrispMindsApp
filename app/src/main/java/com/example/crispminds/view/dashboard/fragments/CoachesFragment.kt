package com.example.crispminds.view.dashboard.fragments


import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.crispminds.R
import com.example.crispminds.adapters.CoachesListAdapter
import com.example.crispminds.adapters.SubCategoryListAdapter
import com.example.crispminds.databinding.FragmentCoachesBinding
import com.example.crispminds.models.CoachDTO
import com.example.crispminds.models.categorydtos.CategoryDTO
import com.example.crispminds.utils.Constants
import com.example.crispminds.utils.Constants.Companion.COACH_DETAILS
import com.example.crispminds.utils.CustomProgressBar
import com.example.crispminds.utils.Utils
import com.example.crispminds.view.dashboard.viewmodel.CoachesFragmentViewModel
import com.example.opposfeapp.utils.SnackbarUtils

/**
 * A simple [Fragment] subclass.
 */
class CoachesFragment : Fragment(), CoachesListAdapter.OnItemClickListener,
    SubCategoryListAdapter.OnSubCategoryItemClickListener {

    lateinit var binding: FragmentCoachesBinding
    lateinit var viewModel: CoachesFragmentViewModel
    lateinit var subCategoryAdapter: SubCategoryListAdapter
    lateinit var coachesListAdapter: CoachesListAdapter
    lateinit var sharedPreferences: SharedPreferences
    private var isSearchClicked = false
    var progressBar = CustomProgressBar()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_coaches, container, false)
        binding = DataBindingUtil.inflate<FragmentCoachesBinding>(
            inflater, R.layout.fragment_coaches,
            container, false
        )
//        viewModel = ViewModelProviders.of(this).get(CoachesFragmentViewModel::class.java)
        viewModel = Utils.obtainBaseObservable(
            activity as AppCompatActivity,
            CoachesFragmentViewModel::class.java,
            this,
            binding.root
        )!!
        binding.viewModel = viewModel

        subCategoryAdapter = SubCategoryListAdapter(this)
        binding.coachesSubCategoryRecyclerView.adapter = subCategoryAdapter

        coachesListAdapter = CoachesListAdapter(this)
        binding.coachesRecyclerView.adapter = coachesListAdapter;


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = Constants.getSharedPreferences(context!!)
        var coachCategoryId: Long? =
            Constants.decrypt(sharedPreferences.getString(Constants.COACHES_CATEGORY_ID, ""))
                ?.toLong()

        viewModel.coachCategoryId = coachCategoryId
//        viewModel.getCoachesSubCategoryData(coachCategoryId!!)
//        viewModel.getCoachesSubCategoryData(coachCategoryId!!)
        viewModel.getCoachesPagination()

        viewModel.coachesSubCategoryArrayList.observe(this, Observer {
            it?.let {
                subCategoryAdapter.subCategoryList = it
            }
        })

        viewModel.coachesDetailsArrayList.observe(this, Observer {
            it?.let {
                viewModel.coachesCount.value = "" + it.size
                coachesListAdapter.coachList = it
            }
        })

        viewModel.showProgressBar.observe(this, Observer {
            if (it)
                progressBar.show(activity as Activity, "Please Wait...")
            else
                progressBar.dialog.dismiss()
        })



        viewModel.searchQueryString.observe(this, Observer {

            val query = viewModel.searchQueryString.value
            if (query?.length!! >= 3) {
                isSearchClicked = true
                viewModel.searchByText(query)
            } else if (isSearchClicked && query.isEmpty()) {
                isSearchClicked = false
                viewModel.populateData()
            }

        })

        viewModel.showMessage.observe(this, Observer {
            if (!it.equals(""))
                SnackbarUtils.showSnackbar(binding.root,it)
        })

    }

    override fun onItemClick(coachDetail: CoachDTO) {
        var bundle = bundleOf(COACH_DETAILS to coachDetail)

        binding.root.findNavController()
            .navigate(R.id.action_coachesFragment_to_coachDetailsFragment, bundle)

    }

    override fun onSubCategoryClick(categoryDTO: CategoryDTO) {
        viewModel.showProgressBar.value = true
        viewModel.getCoachesData( viewModel.coachCategoryId!!,categoryDTO.categoryId!!)
    }
}
