package com.eslam.ebtikartask.Pojo_Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResultsItem{

	@SerializedName("gender")
	private int gender;

	@SerializedName("known_for_department")
	private String knownForDepartment;

	@SerializedName("popularity")
	private double popularity;

	@SerializedName("known_for")
	private List<KnownForItem> knownFor;

	@SerializedName("name")
	private String name;

	@SerializedName("profile_path")
	private String profilePath;

	@SerializedName("id")
	private int id;

	@SerializedName("adult")
	private boolean adult;

	public int getGender(){
		return gender;
	}

	public String getKnownForDepartment(){
		return knownForDepartment;
	}

	public double getPopularity(){
		return popularity;
	}

	public List<KnownForItem> getKnownFor(){
		return knownFor;
	}

	public String getName(){
		return name;
	}

	public String getProfilePath(){
		return profilePath;
	}

	public int getId(){
		return id;
	}

	public boolean isAdult(){
		return adult;
	}
}