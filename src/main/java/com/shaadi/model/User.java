package com.shaadi.model;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    private String
            TAG_NAME = "name",
            TAG_NAME_FIRST = "first",
            TAG_NAME_LAST = "last",
            TAG_NAME_TITLE = "title",
            TAG_GENDER = "gender",
            TAG_LOCATION = "location",
            TAG_STREET = "street",
            TAG_CITY = "city",
            TAG_STATE = "state",
            TAG_POSTCODE = "postcode",
            TAG_DOB = "dob",
            TAG_EMAIL = "email",
            TAG_PHONE = "phone",
            TAG_CELL = "cell",
            TAG_AGE = "age",
            TAG_LARGE = "large",
            TAG_MEDIUM = "medium",
            TAG_THUMBNAIL = "thumbnail",
            TAG_PICTURE = "picture",
            TAG_NATIONALITY = "nationality";

    private String titleName;
    private String firstName;
    private String lastName;
    private String gender;
    private String street;
    private String city;
    private String state;
    private String postCode;
    private String phoneNumber;
    private String cell;
    private String email;

    private int age;
    private String picturThumbnailUrl;
    private String pictureMediumUrl;
    private String pictureLargeUrl;

    private String nationality;
    public User(JSONObject userJson){
        try {
            if(userJson.has(TAG_GENDER)) {
                gender = userJson.getString(TAG_GENDER);
            }
            if(userJson.has(TAG_NAME)) {
                JSONObject userNameJson = userJson.getJSONObject(TAG_NAME);
                if(userNameJson.has(TAG_NAME_TITLE)) {
                    titleName = userNameJson.getString(TAG_NAME_TITLE);
                }
                if(userNameJson.has(TAG_NAME_FIRST)) {
                    firstName = userNameJson.getString(TAG_NAME_FIRST);
                }
                if(userNameJson.has(TAG_NAME_LAST)) {
                    lastName = userNameJson.getString(TAG_NAME_LAST);
                }
            }
            if(userJson.has(TAG_LOCATION)) {
                JSONObject userLocationJson = userJson.getJSONObject(TAG_LOCATION);
                if(userLocationJson.has(TAG_STREET)) {
                    street = userLocationJson.getString(TAG_STREET);
                }
                if(userLocationJson.has(TAG_CITY)) {
                    city = userLocationJson.getString(TAG_CITY);
                }
                if(userLocationJson.has(TAG_POSTCODE)) {
                    postCode = userLocationJson.getString(TAG_POSTCODE);
                }
                if(userLocationJson.has(TAG_STATE)) {
                    state = userLocationJson.getString(TAG_STATE);
                }
            }

            if(userJson.has(TAG_EMAIL)) {
                email = userJson.getString(TAG_EMAIL);
            }

            if(userJson.has(TAG_DOB)){
                JSONObject dobJson = userJson.getJSONObject(TAG_DOB);
                if(dobJson.has(TAG_AGE)){
                    age = dobJson.getInt(TAG_AGE);
                }
            }

            if(userJson.has(TAG_PHONE)){
                phoneNumber = userJson.getString(TAG_PHONE);
            }

            if(userJson.has(TAG_CELL)){
                cell = userJson.getString(TAG_CELL);
            }

            if(userJson.has(TAG_PICTURE)){
                JSONObject pictureJson = userJson.getJSONObject(TAG_PICTURE);
                if(pictureJson.has(TAG_LARGE)){
                    pictureLargeUrl = pictureJson.getString(TAG_LARGE);
                }
                if(pictureJson.has(TAG_MEDIUM)){
                    pictureMediumUrl = pictureJson.getString(TAG_MEDIUM);
                }
                if(pictureJson.has(TAG_THUMBNAIL)){
                    picturThumbnailUrl = pictureJson.getString(TAG_THUMBNAIL);
                }
            }

            if(userJson.has(TAG_NATIONALITY)){
                nationality = userJson.getString(TAG_NATIONALITY);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getTitleName() {
        return titleName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCell() {
        return cell;
    }

    public String getEmail() {
        return email;
    }

    public String getPicturThumbnailUrl() {
        return picturThumbnailUrl;
    }

    public String getPictureMediumUrl() {
        return pictureMediumUrl;
    }

    public String getPictureLargeUrl() {
        return pictureLargeUrl;
    }

    public String getState() {
        return state;
    }

    public int getAge() {
        return age;
    }

    public String getNationality() {
        return nationality;
    }
}
