<?php

use Illuminate\Http\Request;
//use Illuminate\Routing\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/
Route::get('search/{mail}/{password}','peopleController@search');
Route::post('store','peopleController@store');
Route::get('get_id','peopleController@get_id');
Route::post('store_tran','transactionController@store_tran');
Route::get('update_points/{id}/{p}','peopleController@update_points');
Route::get('get_points/{id}','peopleController@get_points');
Route::get('report/{id}','peopleController@report');
Route::post('addImage','imageController@addImage');
Route::get('getImage/{id}','imageController@getImage');
Route::post('changeImage/{id}','imageController@changeImage');
