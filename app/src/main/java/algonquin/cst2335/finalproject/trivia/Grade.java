package algonquin.cst2335.finalproject.trivia;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
/**
 * Represents a Grade entity for the Trivia game.
 * This class is used to store information about a player's grade in a specific category and question number.
 */
@Entity
public class Grade {
    /**
     * The username of the player who received the grade.
     */
    @ColumnInfo(name="username")
    String username;

    /**
     * The grade received by the player for the specific question.
     */
    @ColumnInfo(name="grade")
    double grade;

    /**
     * The question number for which the grade is assigned.
     */
    @ColumnInfo(name="questionNumber")
    int questionNumber;
    /**
     * The timestamp when the grade was sent.
     */
    @ColumnInfo(name = "timesent")
    String timesent;
    /**
     * The category of the question for which the grade is assigned.
     */
    @ColumnInfo(name="category")
    String category;
    /**
     * The primary key auto-generated for the grade entity.
     */
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo (name="id")
    public long id;
    /**
     * Constructs a new Grade object with the specified parameters.
     *
     * @param u The username of the player.
     * @param g The grade received for the specific question.
     * @param qn The question number for which the grade is assigned.
     * @param t The timestamp when the grade was sent.
     * @param c The category of the question for which the grade is assigned.
     */
    Grade(String u,double g,int qn, String t,String c){
        username = u;
        grade = g;
        questionNumber = qn;
        timesent = t;
        category = c;
    }
    /**
     * Default constructor for the Grade class.
     */
    Grade(){};
    /**
     * Gets the username of the player who received the grade.
     *
     * @return The username of the player.
     */
    public String getUsername(){
        return username;
    }
    /**
     * Gets the timestamp when the grade was sent.
     *
     * @return The timestamp when the grade was sent.
     */
    public String getTimesent(){
        return timesent;
    }
    /**
     * Gets the grade received by the player for the specific question.
     *
     * @return The grade received by the player.
     */
    public double getGrade(){
        return grade;
    }
    /**
     * Gets the category of the question for which the grade is assigned.
     *
     * @return The category of the question.
     */
    public String getCategory() {
        return category;
    }
    /**
     * Sets the primary key for the grade entity.
     *
     * @param id The primary key to set.
     */
    public void setId(long id) {
        this.id = id;
    }
    /**
     * Gets the question number for which the grade is assigned.
     *
     * @return The question number.
     */
    public int getQuestionNumber() {
        return questionNumber;
    }

}
