package ohtu.cucumberTest;

import cucumber.api.java.en.Given;

public class successBookmarksCreationStepdefs extends AbstractStepdefs {

    @Given("^two blogposts have been created and saved to the database$")
    public void two_blogposts_have_been_created_and_saved_to_the_database() throws Throwable {
        addNewBlogpost("Data Mining", "navamani saravanan", "http://notescompsci.blogspot.com/2013/04/data-mining.html");
        addNewBlogpost("SoftWare Testing", "navamani saravanan", "http://notescompsci.blogspot.com/2013/04/software-testing.html");
    }

    @Given("^two videos have been created and saved to the database$")
    public void two_videos_have_been_created_and_saved_to_the_database() throws Throwable {
        addNewVideo("Map of Computer Science", "https://www.youtube.com/watch?v=SzJ46YA_RaA");
        addNewVideo("Java tutorial", "https://www.youtube.com/watch?v=grEKMHGYyns");
    }

    @Given("^two books have been created and saved to the database$")
    public void two_books_have_been_created_and_saved_to_the_database() throws Throwable {
        addNewBook("Introduction to Algorithms", "Thomas H. Cormen", "9-780-262-0338-48");
        addNewBook("Learning Python", "Mark Lutz", "9-781-593-2760-34");
    }

    @Given("^new bookmarks have been created and saved to the database$")
    public void new_bookmarks_have_been_created_and_saved_to_the_database() throws Throwable {
        addNewBookmarks();
    }

    private void addNewBookmarks() { //other types will be added soon
        addNewBlogpost("Data Mining in computer science", "navamani saravanan", "http://notescompsci.blogspot.com/2013/04/data-mining.html");
        addNewVideo("Map of Computer Science", "https://www.youtube.com/watch?v=SzJ46YA_RaA");
        addNewBook("Learning Python", "Mark Lutz", "9-781-593-2760-34");
    }
    
    private void addNewBlogpost(String title, String author, String url) {
        inputs.add("A");
        inputs.add("B");
        inputs.add(title);
        inputs.add(author);
        inputs.add(url);
    }

    private void addNewVideo(String title, String url) {
        inputs.add("A");
        inputs.add("V");
        inputs.add(title);
        inputs.add(url);
    }

    private void addNewBook(String title, String author, String isbn) {
        inputs.add("A");
        inputs.add("K");
        inputs.add(title);
        inputs.add(author);
        inputs.add(isbn);
    }

}
