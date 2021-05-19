<?php

namespace App\Http\Controllers;


use App\transaction;
use http\Exception\InvalidArgumentException;
use Illuminate\Http\Request;
use phpDocumentor\Reflection\Types\Integer;

class transactionController extends Controller
{
    public function search(Integer $given_id,Integer $taken_id)
    {

        $search_transaction = transaction::where('given_id', '=', "{$given_id}")
            ->Where('taken_id','=',"{$taken_id}")->get();
        return response()->json($search_transaction);

    }
    public function store_tran(Request $request)
    {
        $request->validate([
            'given_id'=>'required',
            'taken_id'=>'required',
            'points'=>'required',
            'date'=>'required'
        ]);

        $transaction=transaction::create($request->all());

        return response()->json($transaction);
    }

}
