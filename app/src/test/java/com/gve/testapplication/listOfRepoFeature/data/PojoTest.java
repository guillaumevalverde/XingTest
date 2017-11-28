package com.gve.testapplication.listOfRepoFeature.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gve.testapplication.ListOfRepoFeature.data.OwnerRaw;
import com.gve.testapplication.ListOfRepoFeature.data.RepoRaw;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by gve on 28/11/2017.
 */

public class PojoTest {

    private Gson gson;

    @Before
    public void setUp() {
        final GsonBuilder builder = new GsonBuilder();
        gson = builder.create();
    }

    @Test
    public void deserializeOwnerRawTest() {
        OwnerRaw ownerRaw = gson.fromJson(PojoUtils.OWNER_RAW, OwnerRaw.class);

        assertEquals(PojoUtils.OWNER_RAW_LOGIN, ownerRaw.getLogin());
        assertEquals(PojoUtils.OWNER_RAW_ID, ownerRaw.getId());
    }

    @Test
    public void deserializeRepoRawTest() {
        Type listType = new TypeToken<ArrayList<RepoRaw>>(){}.getType();
        List<RepoRaw> listRepoRaw = gson.fromJson(PojoUtils.LIST_2_REPO_RAW, listType);

        assertEquals(2, listRepoRaw.size());
    }
}
