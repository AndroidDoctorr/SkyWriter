package com.andrewtorr.skywriter.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.util.ArrayList;

/**
 * Created by Andrew on 10/17/2015.
 *
 */
@ParseClassName("Scene")
public class Scene extends ParseObject {
    private Script script;
    private Integer sceneNumber;
    private String sceneTitle;
    private ArrayList<String> characters;


}
