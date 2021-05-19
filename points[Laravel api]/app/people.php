<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class people extends Model
{
    protected $fillable=[
        'name',
        'mail',
        'password',
        'status',
        'points',
        'qr_code'
    ];
    protected $primaryKey = 'id';
    public $timestamps = false;
}
