package com.example.crispminds.master_controller.sync

import android.content.Context
import android.widget.Toast
import com.example.crispminds.master_controller.source.MasterRepository
import com.example.crispminds.modeldtos.EmotionMasterDTO
import com.example.crispminds.models.categorydtos.CategoryDTO
import com.example.crispminds.retrofit_services.network_utils.IDataSourceCallback

class SyncData {

    companion object {
        @Volatile
        var masterRepository: MasterRepository? = null

        /**
         * Performs the network request for updated weather, parses the JSON from that request, and
         * inserts the new weather information into our ContentProvider. Will notify the user that new
         * weather has been loaded if the user hasn't been notified of the weather within the last day
         * AND they haven't disabled notifications in the preferences screen.
         *
         * @param context Used to access utility methods and the ContentResolver
         */

        @Synchronized
        fun syncData(context: Context?) {
            masterRepository = MasterRepository.getInstance(context)

            /*--------  Category section -----------*/
            try {

                masterRepository?.fetchCategoryMasterDataRemote(object :
                    IDataSourceCallback<List<CategoryDTO>> {

                    override fun onDataFound(data: List<CategoryDTO>) {

                    }

                    override fun onError(error: String?) {
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                    }

                })

            } catch (e: Exception) {
                e.printStackTrace()
            }

            /*--------  Emotion section -----------*/

            try {
                masterRepository?.fetchEmotionMastersRemote(object :
                    IDataSourceCallback<List<EmotionMasterDTO>> {

                    override fun onDataFound(data: List<EmotionMasterDTO>) {

                    }

                    override fun onError(error: String?) {
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                    }

                })
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}