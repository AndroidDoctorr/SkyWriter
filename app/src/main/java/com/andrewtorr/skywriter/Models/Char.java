package com.andrewtorr.skywriter.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Andrew on 10/17/2015.
 *
 */
@ParseClassName("Char")
public class Char extends ParseObject {
    private String name;
    private Script script;

}
