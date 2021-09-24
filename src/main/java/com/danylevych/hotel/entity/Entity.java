package com.danylevych.hotel.entity;

import java.io.Serializable;

public interface Entity extends Serializable {

    Object[] extract();

   //Object[] getInsertColumns();
}
