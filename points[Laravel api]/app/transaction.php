<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class transaction extends Model
{
    protected $fillable=[
        'given_id',
        'taken_id',
        'points',
         'date'
    ];
    protected $primaryKey = 'id';
    public $timestamps = false;
}
