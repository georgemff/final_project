package com.final_project.final_project;

public class AddUserResponse {
    public String userID;
    public String username;
    public BooksItem[] books;
}

class BooksItem {
    public String isbn;
}

/*

{
        "userId": "string",
            "username": "string",
            "books": [
        {
            "isbn": "string",
                "title": "string",
                "subTitle": "string",
                "author": "string",
                "publish_date": "2022-06-29T14:01:13.165Z",
                "publisher": "string",
                "pages": 0,
                "description": "string",
                "website": "string"
        }
  ]
    }

 */
