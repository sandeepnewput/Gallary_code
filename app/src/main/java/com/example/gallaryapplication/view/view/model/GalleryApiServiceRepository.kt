package com.example.gallaryapplication.view.view.model

import android.app.Application
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import android.util.Log

class GalleryApiServiceRepository(application: Application) {

    var resolver = application.contentResolver
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
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                while (cursor.moveToNext()) {
                    imageList.add(
                        ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            cursor.getLong(idColumn)
                        ).toString()
                    )
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
                val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
                while (cursor.moveToNext()) {
                    videoList.add(
                        ContentUris.withAppendedId(
                            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                            cursor.getLong(idColumn)
                        ).toString()
                    )
                }
            } else {
                Log.d("AddViewModel", "Cursor is null!")
            }
        }
        return videoList
    }//end of getAllvideo function


//    fun getAllMusic(): List<AudioModel> {
//
//
//        val musicProjection = arrayOf(
//            MediaStore.Audio.Media._ID,
//            MediaStore.Audio.Media.DISPLAY_NAME
//        )
//
//        val musicSortOrder = "${MediaStore.Audio.Media.DATE_ADDED} DESC"
//
//        val cursor = resolver.query(
//            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//            musicProjection,
//            null,
//            null,
//            musicSortOrder
//        )
//        val musicList = ArrayList<AudioModel>()
//        cursor?.use {
//
//            if (cursor != null) {
//                val idColumn =
//                    cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
//                while (cursor.moveToNext()) {
//
//                    val name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME))
//                    val uri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,cursor.getLong(idColumn))
//
//                    musicList.add(AudioModel(uri.toString(),name))
//                }
//            } else {
//                Log.d("AddViewModel", "Cursor is null!")
//            }
//        }
//        return musicList
//    }//end of getAllImage




}//end of GallaryApiServiceRepository