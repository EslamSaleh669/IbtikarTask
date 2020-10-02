package com.eslam.ebtikartask.Pojo_Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Person{

	@SerializedName("page")
	public int page;

	@SerializedName("total_pages")
	public int totalPages;

	@SerializedName("results")
	public List<ResultsItem> results;

	@SerializedName("total_results")
	public int totalResults;

	public int getPage(){
		return page;
	}

	public int getTotalPages(){
		return totalPages;
	}

	public List<ResultsItem> getResults(){
		return results;
	}

	public int getTotalResults(){
		return totalResults;
	}
}