<?php

namespace App\Http\Controllers;
use App\people;
use App\transaction;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;

use phpDocumentor\Reflection\Types\Integer;

class peopleController extends Controller
{
    public function search(String $mail,String $password)
    {

        $search_people = people::where('mail', '=', "{$mail}")
        ->Where('password','=',"{$password}")->get();
        return response()->json($search_people);

    }
    public function get_id(){
        $statement = DB::select("SHOW TABLE STATUS LIKE 'people'");
        $get_id= $statement[0]->Auto_increment;
        return response()->json($get_id);
    }
    public function store(Request $request)
    {
        $request->validate([
            'name'=>'required',
            'mail'=>'required',
            'password'=>'required',
            'status'=>'required',
            'points'=>'required',
            'qr_code'=>'required'
        ]);

        $people=people::create($request->all());

        return response()->json($people);
    }
  public function update_points(int $id,int $p){
        $points=DB::select("Select points from people where id=+'$id'");

        DB::table('people')
            ->where('id',$id)
            ->update(['points'=>$points[0]->points+$p]);

  }
 public function get_points(int $id){
     $points=DB::select("Select points from people where id=+'$id'");
      return $points[0]->points;
 }
 public function report(int $id){
     $users = DB::table('transactions')
         ->join('people as a', 'a.id', '=', 'transactions.given_id')
         ->join('people as b','b.id','=','transactions.taken_id')
         ->select('a.name as given','transactions.given_id','b.name as taken','transactions.taken_id','transactions.points','transactions.date')
         ->where('a.id',$id)
         ->orWhere('b.id',$id)
         ->get();

     return response()->json($users);
 }

}
