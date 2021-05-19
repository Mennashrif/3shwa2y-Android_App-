<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class image extends Model
{
    protected $fillable=[
        'User_id',
        'image'
    ];
    protected $primaryKey = 'id';
    public $timestamps = false;
}
