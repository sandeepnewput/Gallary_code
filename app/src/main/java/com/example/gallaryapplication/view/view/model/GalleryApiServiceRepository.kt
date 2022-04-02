package com.example.gallaryapplication.view.view.model

import android.app.Application
import android.content.ContentUris
import android.provider.MediaStore
import android.util.Log

class GalleryApiServiceRepository(application: Application) {

   private val resolver = application.contentResolver

    fun getAllImages(): List<String> {

        val imageProjection = arrayOf(
            MediaStore.Images.Media._ID
        )

        val imageSortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        val cursor = resolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            imageProjection,
            null,
            null,
            imageSortOrder
        )
        val imageList = ArrayList<String>()
        cursor?.use {

            if (cursor != null) {

                while (cursor.moveToNext()) {
                    try {
                        imageList.add(
                            ContentUris.withAppendedId(
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID))
                            ).toString()
                        )
                    }catch (e: IllegalArgumentException){
                        e.printStackTrace()
                    }

                }
            } else {
                Log.d("AddViewModel", "Cursor is null!")
            }
        }
        return imageList
    }//end of getAllImage

    fun getAllVideo(): List<String> {

        val videoProjection = arrayOf(
            MediaStore.Video.Media._ID,

            )

        val videoSortOrder = "${MediaStore.Video.Media.DATE_ADDED} DESC"

        val cursor = resolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            videoProjection,
            null,
            null,
            videoSortOrder
        )
        val videoList = ArrayList<String>()
        cursor?.use {

            if (cursor != null) {

                while (cursor.moveToNext()) {
                    try {
                        videoList.add(
                            ContentUris.withAppendedId(
                                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                                cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID))
                            ).toString()
                        )
                    }catch (e: IllegalArgumentException){
                        e.printStackTrace()
                    }

                }
            } else {
                Log.d("AddViewModel", "Cursor is null!")
            }
        }
        return videoList
    }//end of getAllvideo function


    fun getAllMusic(): List<MusicModel> {


        val musicProjection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.SIZE

        )

        val musicSortOrder = "${MediaStore.Audio.Media.DATE_ADDED} DESC"

        val cursor = resolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            musicProjection,
            null,
            null,
            musicSortOrder
        )
        val musicList = ArrayList<MusicModel>()
        cursor?.use {

            if (cursor != null) {


                while (cursor.moveToNext()) {
                    try{
                        val name =
                            cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME))

                        val uri = ContentUris.withAppendedId(
                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                            cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID))
                        )

                        val size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE))

                        musicList.add(MusicModel(uri.toString(), name,size))

                    }catch (e: IllegalArgumentException){
                        e.printStackTrace()
                    }

                }
            } else {
                Log.d("AddViewModel", "Cursor is null!")
            }
        }
        return musicList
    }//end of getAllImage


}//end of GallaryApiServiceRepository