<?php

namespace App\Http\Controllers;

use App\image;
use http\Message\Body;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
class imageController extends Controller
{
    public function addImage(Request $request)
    {
        $request->validate([
            'User_id' => 'required',
            'image' => 'required'
        ]);

        $image = image::create($request->all());

        return response()->json($image);
    }
    public function getImage(int $id){
        $image=image::where('User_id','=',"{$id}")->get();
        return response()->json($image);
    }
    public function changeImage(int $id,Request $request){

               $image=$request->input('image');
                DB::table('images')
                    ->where('User_id',$id)
                    ->update(['image'=>$image]);





    }
}
