package com.example.myquiztest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.myquiztest.ContractQuiz.*;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DatabaseQuiz extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "MyQuizTest.db";
    public static final int DATABASE_VERSION = 1;

    private static  DatabaseQuiz instance;
    private SQLiteDatabase database;

    private DatabaseQuiz(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DatabaseQuiz getInstance(Context context){
        if (instance == null){
            instance = new DatabaseQuiz(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        this.database = database;

        final String SQL_CREATE_SUBJECT_TABLE = "CREATE TABLE " +
                SubjectTable.TABLE_SUBJECT + "( " +
                SubjectTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SubjectTable.COLUMN_SUBJECT + " TEXT " +
                ")";

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionTable.TABLE_NAME + " ( " +
                QuestionTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionTable.COLUMN_QUESTION + " TEXT, " +
                QuestionTable.COLUMN_OPTIONA + " TEXT, " +
                QuestionTable.COLUMN_OPTIONB + " TEXT, " +
                QuestionTable.COLUMN_OPTIONC + " TEXT, " +
                QuestionTable.COLUMN_OPTIOND + " TEXT, " +
                QuestionTable.COLUMN_ANSWERNUMBER + " INTEGER, " +
                QuestionTable.COLUMN_DIFFICULT + " TEXT, " +
                QuestionTable.COLUMN_CONNECT_ID + " INTEGER, " +
                "FOREIGN KEY(" + QuestionTable.COLUMN_CONNECT_ID + ") REFERENCES " +
                SubjectTable.TABLE_SUBJECT + "(" + SubjectTable._ID + ")" + "ON DELETE CASCADE" +
                ")";
        database.execSQL(SQL_CREATE_SUBJECT_TABLE);
        database.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillSubjectsTable();
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + SubjectTable.TABLE_SUBJECT);
        database.execSQL("DROP TABLE IF EXISTS " + QuestionTable.TABLE_NAME);
        onCreate(database);
    }

    @Override
    public void onConfigure(SQLiteDatabase database) {
        super.onConfigure(database);
        database.setForeignKeyConstraintsEnabled(true);
    }

    private void fillSubjectsTable(){
        SubjectActivity subject1 = new SubjectActivity("Math");
        insertSubject(subject1);
        SubjectActivity subject2 = new SubjectActivity("English");
        insertSubject(subject2);
        SubjectActivity subject3 = new SubjectActivity("Geography");
        insertSubject(subject3);
    }

    public void addSubject(SubjectActivity subjectActivity){
        database = getWritableDatabase();
        insertSubject(subjectActivity);
    }

    public void addSubjects(List<SubjectActivity> subjectActivities){
        database = getWritableDatabase();
        for (SubjectActivity subjectActivity : subjectActivities){
            insertSubject(subjectActivity);
        }
    }

    private void insertSubject(SubjectActivity subjectActivity){
        ContentValues contentValues = new ContentValues();
        contentValues.put(SubjectTable.COLUMN_SUBJECT, subjectActivity.getNameSubject());
        database.insert(SubjectTable.TABLE_SUBJECT, null, contentValues);
    }

    private void fillQuestionsTable(){
        Question question1 = new Question("What is the comparative of \"hot\"?", "hoter", "hotter", "hotest", "hottest", 2, Question.DIFFICULT_EASY, SubjectActivity.ENGLISH);
        insertQuestions(question1);
        Question question2 = new Question("What is the superlative of \"ugly\"?", "uglier", "uggliest", "uglyest", "ugliest", 4, Question.DIFFICULT_EASY, SubjectActivity.ENGLISH);
        insertQuestions(question2);
        Question question3 = new Question("________ computer is a Mac, but ________ is a PC.", "Your, mine", "Yours, mine", "Your, my", "Yours, my", 1, Question.DIFFICULT_EASY, SubjectActivity.ENGLISH);
        insertQuestions(question3);
        Question question4 = new Question("This bird has broken ________ wing.", "it's", "its'", "hers", "its", 4, Question.DIFFICULT_MEDIUM, SubjectActivity.ENGLISH);
        insertQuestions(question4);
        Question question5 = new Question("When they heard the bad news, they were very ____________.", "sadly", "sad", "sadder", "saddest", 2, Question.DIFFICULT_EASY, SubjectActivity.ENGLISH);
        insertQuestions(question5);
        Question question6 = new Question("Barbara swims ____________ than Anna because she has stronger arms.", "fastlier", "fast", "faster", "fasttest", 3, Question.DIFFICULT_EASY, SubjectActivity.ENGLISH);
        insertQuestions(question6);
        Question question7 = new Question("They cancelled the soccer match because ________ snowing.", "there were", "there was", "it was", "it were", 3, Question.DIFFICULT_MEDIUM, SubjectActivity.ENGLISH);
        insertQuestions(question7);
        Question question8 = new Question("__________ warmly so you don't get cold outside. It's snowing!", "Dress", "Don't dress", "Dressing", "Didn't dress", 1, Question.DIFFICULT_MEDIUM, SubjectActivity.ENGLISH);
        insertQuestions(question8);
        Question question9 = new Question("How many pages ________ in your grammar book?", "there are", "is it", "are there", "it is", 3, Question.DIFFICULT_EASY, SubjectActivity.ENGLISH);
        insertQuestions(question9);
        Question question10 = new Question("Teacher: Jerry, __________ please. I'm ready to begin teaching.", "you sit down", "sit down", "You take", "Be", 2, Question.DIFFICULT_EASY, SubjectActivity.ENGLISH);
        insertQuestions(question10);
        Question question11 = new Question("How many cents is equal to $ ¼?", "15", "20", "25", "30", 3, Question.DIFFICULT_EASY, SubjectActivity.MATH);
        insertQuestions(question11);
        Question question12 = new Question("The least number of five digits which is completely divisible by 39, is ________", "10101", "10062", "10016", "10023", 4, Question.DIFFICULT_MEDIUM, SubjectActivity.MATH);
        insertQuestions(question12);
        Question question13 = new Question("What razor-thin country accounts for more than half of the western coastline of South America?", "Bolivia", "Chile", "Ecuador", "Peru", 2, Question.DIFFICULT_MEDIUM, SubjectActivity.GEOGRAPHY);
        insertQuestions(question13);
        Question question14 = new Question("What country has the most natural lakes?", "Canada", "Australia", "USA", "India", 1, Question.DIFFICULT_MEDIUM, SubjectActivity.GEOGRAPHY);
        insertQuestions(question14);
        Question question15 = new Question("If the number X 78 Y is divisible by 55 then value of X and Y are:", "1, 0", "4, 5", "6, 5", "No answer", 2, Question.DIFFICULT_MEDIUM, SubjectActivity.MATH);
        insertQuestions(question15);
        Question question16 = new Question("What is the driest place on Earth?", "Sahara desert", "Kufra, Libya", "Atacama desert", "Mcmurdo, Antarctica", 4, Question.DIFFICULT_HARD, SubjectActivity.GEOGRAPHY);
        insertQuestions(question16);
        Question question17 = new Question("Fill in the blanks; 4, 6, 12, 14, 28, 30, (?)", "60", "62", "64", "32", 1, Question.DIFFICULT_EASY, SubjectActivity.MATH);
        insertQuestions(question17);
        Question question18 = new Question("Each edge of a cube is increased by 50%. What will be the percent increase in its volume?", "50%", "150%", "133 ¼ %", "237 ½ %", 4, Question.DIFFICULT_HARD, SubjectActivity.MATH);
        insertQuestions(question18);
        Question question19 = new Question("What African country served as the setting for Tatooine in Star Wars?", "Tunisia", "Gabon", "Ghana", "Ethiopia", 1, Question.DIFFICULT_HARD, SubjectActivity.GEOGRAPHY);
        insertQuestions(question19);
        Question question20 = new Question("__________ logging provides jobs and profits, the government is reluctant to control it.", "So", "Consequently", "Due to", "Since", 4, Question.DIFFICULT_HARD, SubjectActivity.ENGLISH);
        insertQuestions(question20);
        Question question21 = new Question("Which number occurred before 9019?", "9099", "9109", "9091", "None of these.", 4, Question.DIFFICULT_EASY, SubjectActivity.MATH);
        insertQuestions(question21);
        Question question22 = new Question("My friend promised to pick me up at 7:00. It's now 7:25 and I don't see him. He _________", "must have forgotten", "might have forgotten", "might have forgotten", "might forget", 1, Question.DIFFICULT_MEDIUM, SubjectActivity.ENGLISH);
        insertQuestions(question22);
        Question question23 = new Question("What mountains form part of the conventional boundary between the continents of Europe and Asia?", "Ural ", "Appalachian", "Himalayas", "Andes", 1, Question.DIFFICULT_MEDIUM, SubjectActivity.GEOGRAPHY);
        insertQuestions(question23);
        Question question24 = new Question("What is the most populated nation in South America?", "Brazil", "Venezuela", "Columbia", "Argentina", 1, Question.DIFFICULT_EASY, SubjectActivity.GEOGRAPHY);
        insertQuestions(question24);
        Question question25 = new Question("$ 2496 is spend in the floor repair of 30 × 16 ft hall. What is repair cost per square feet?", "$ 5.20", "$ 78.00", "$ 12.48", "$ 52.00", 1, Question.DIFFICULT_HARD, SubjectActivity.MATH);
        insertQuestions(question25);
        Question question26 = new Question("(9321 + 5406 + 1001) ÷ (498 + 929 + 660) = _____", "3.5", "4.5", "16.5", "7.5", 4, Question.DIFFICULT_EASY, SubjectActivity.MATH);
        insertQuestions(question26);
        Question question27 = new Question("What are the only landlocked countries in South America?", "Peru, Columbia", "Paraguay, Bolivia", "Uruguay, Suriname", "Brazil, Argentina", 2, Question.DIFFICULT_MEDIUM, SubjectActivity.GEOGRAPHY);
        insertQuestions(question27);
        Question question28 = new Question("What is the smallest independent country on Earth?", "Nauru", "Vatican", "Grenada", "Monaco", 2, Question.DIFFICULT_MEDIUM, SubjectActivity.GEOGRAPHY);
        insertQuestions(question28);
        Question question29 = new Question("Many aspects of popular Asian culture come from _____ Chinese.", "a", "an", "the", "None of these", 3, Question.DIFFICULT_EASY, SubjectActivity.ENGLISH);
        insertQuestions(question29);
        Question question30 = new Question("Which least number should be added to 2600 to make it a perfect square?", "3", "39", "1", "15", 3, Question.DIFFICULT_MEDIUM, SubjectActivity.MATH);
        insertQuestions(question30);
        Question question31 = new Question("What is 121 times 11?", "1331", "1313", "1133", "3131", 1, Question.DIFFICULT_EASY, SubjectActivity.MATH);
        insertQuestions(question31);
        Question question32 = new Question("Factors of 9 are___", "1, 2 and 3", "1, 2, 3 and 9", "1, 6 and 9", "None of these.", 4, Question.DIFFICULT_EASY, SubjectActivity.MATH);
        insertQuestions(question32);
        Question question33 = new Question("What is the unit of volume?", "square units.", "cubic units.", "only unit.", "None of these.", 2, Question.DIFFICULT_EASY, SubjectActivity.MATH);
        insertQuestions(question33);
        Question question34 = new Question("Arrange the numbers in ascending order: 36, 12, 29, 21, 7.", "36, 29, 21, 12, 7", "36, 29, 7, 21, 12", "7, 12, 21, 29, 36", "None of these", 3, Question.DIFFICULT_EASY, SubjectActivity.MATH);
        insertQuestions(question34);
        Question question35 = new Question("How much is 190 – 87 + 16?", "103", "261", "87", "119", 4, Question.DIFFICULT_EASY, SubjectActivity.MATH);
        insertQuestions(question35);
        Question question36 = new Question(" Complete the sequence 13, 16,____, 22.", "17", "18", "19", "20", 3, Question.DIFFICULT_EASY, SubjectActivity.MATH);
        insertQuestions(question36);
        Question question37 = new Question("Which term is used to identify a number?", "Constant.", "Variable.", "Both a and b.", "None of these.", 1, Question.DIFFICULT_MEDIUM, SubjectActivity.MATH);
        insertQuestions(question37);
        Question question38 = new Question("I am a number. I have 7 in the ones place. I am less than 80 but greater than 70. What is my number?", "71", "73", "75", "77", 4, Question.DIFFICULT_MEDIUM, SubjectActivity.MATH);
        insertQuestions(question38);
        Question question39 = new Question("Two numbers are in ratio 4 : 5. If the sum of the numbers is 135, find the numbers.", "60 and 75", "50 and 65", "70 and 95", "65 and 75", 1, Question.DIFFICULT_MEDIUM, SubjectActivity.MATH);
        insertQuestions(question39);
        Question question40 = new Question("A car can cover a distance of 522 km on 36 liters of petrol. How far can it travel on 14 liters of petrol?", "213 km.", "223 km.", "203 km.", "302 km.", 3, Question.DIFFICULT_MEDIUM, SubjectActivity.MATH);
        insertQuestions(question40);
        Question question41 = new Question("What temperature at Celsius scale is equal to 300°K.", "30° C", "27° C", "300° C", "None of these.", 2, Question.DIFFICULT_MEDIUM, SubjectActivity.MATH);
        insertQuestions(question41);
        Question question42 = new Question("The diagonals of a rhombus are 30 cm and 40 cm long. Find its side.?", "25 cm.", "120 cm.", "100 cm.", "200 cm.", 1, Question.DIFFICULT_MEDIUM, SubjectActivity.MATH);
        insertQuestions(question42);
        Question question43 = new Question("The number which is neither prime nor composite is_____", "3", "0", "1", "2", 2, Question.DIFFICULT_MEDIUM, SubjectActivity.MATH);
        insertQuestions(question43);
        Question question44 = new Question("Nil is 23 years 1 month old, Shelly is 18 years 7 months old and Ben is as much older than Shelly is younger than Nil. The age of Ben is___", "18 years 6 months.", "21 years 11 months.", "20 years 9 months.", "20 years 10 months.", 4, Question.DIFFICULT_HARD, SubjectActivity.MATH);
        insertQuestions(question44);
        Question question45 = new Question("Three bells toll at the intervals of 10, 15 and 24 minutes. All the three begin to toll together at 8 A.M. At what time they will again toll together?", "10.45 A.M.", "10 A.M.", "9.25 A.M.", "8.50 A.M.", 2, Question.DIFFICULT_HARD, SubjectActivity.MATH);
        insertQuestions(question45);
        Question question46 = new Question("A car covers a distance of 200km in 2 hours 40 minutes, whereas a jeep covers the same distance in 2 hours. What is the ratio of their speed?", "3:4", "4:3", "4:5", "5:4", 3, Question.DIFFICULT_HARD, SubjectActivity.MATH);
        insertQuestions(question46);
        Question question47 = new Question("The sum of squares of two numbers is 80 and the square of difference between the two numbers is 36. Find the product of two numbers.", "11", "22", "33", "26", 2, Question.DIFFICULT_HARD, SubjectActivity.MATH);
        insertQuestions(question47);
        Question question48 = new Question("If there are 23 boys and 21 boys are in class A, Class B has 21 boys and 22 girls, how many girls and boys are there in Class A and B?", "73", "69", "87", "91", 3, Question.DIFFICULT_HARD, SubjectActivity.MATH);
        insertQuestions(question48);
        Question question49 = new Question("Your mom is in the market,she buy 22kg of fish and 24kg of chicken,you ate 4kg of chicken and 12kg of fish,how many kg was left of the 2 foods?", "34kg", "37kg", "41kg", "30kg", 4, Question.DIFFICULT_HARD, SubjectActivity.MATH);
        insertQuestions(question49);
        Question question50 = new Question("A clock strikes once at 1 o’clock, twice at 2 o’clock, thrice at 3 o’clock and so on. How many times will it strike in 24 hours?", "78", "136", "156", "196", 3, Question.DIFFICULT_HARD, SubjectActivity.MATH);
        insertQuestions(question50);
        Question question51 = new Question("$ 600 becomes $ 720 in 4 years when the interest is simple. If the rate of interest is increased by 2%, then what will be total amount?", "$ 642", "$ 724", "$ 725", "$ 768", 4, Question.DIFFICULT_HARD, SubjectActivity.MATH);
        insertQuestions(question51);
        Question question52 = new Question("Angelina is a ____________ student. She is kind and pleasant.", "love", "lovely", "bad", "badly", 2, Question.DIFFICULT_EASY, SubjectActivity.ENGLISH);
        insertQuestions(question52);
        Question question53 = new Question("Are you familiar enough ____________", "this city to recommend a good restaurant?", "for this city to recommend a good restaurant?", "to this city to recommend a good restaurant?", "with this city to recommend a good restaurant?", 4, Question.DIFFICULT_EASY, SubjectActivity.ENGLISH);
        insertQuestions(question53);
        Question question54 = new Question("Workers often go on strike when their salaries don't keep pace with increases in the cost ____________", "of living.", "with living.", "to living.", "about living.", 1, Question.DIFFICULT_MEDIUM, SubjectActivity.ENGLISH);
        insertQuestions(question54);
        Question question55 = new Question("Choose the correct sentence.", "Do you know whether or not they are going to have a large wedding?", "I wonder why is the cafeteria is so crowded today?", "Do you know where are they going to stay while they are visiting Vancouver?", "None of these", 1, Question.DIFFICULT_MEDIUM, SubjectActivity.ENGLISH);
        insertQuestions(question55);
        Question question56 = new Question("Choose the answer which is a complex sentence.", "We know junk food is unhealthy, but most of us eat it anyway.", "People that eat a lot of junk food should change their diet.", "Hamburgers, fries and other fast foods are convenient, so many people eat them.", "Eating junk food isn't healthy.", 2, Question.DIFFICULT_MEDIUM, SubjectActivity.ENGLISH);
        insertQuestions(question56);
        Question question57 = new Question("Choose the answer which is a complex sentence.", "She saw a strange light after they heard a noise.", "They heard a noise, and then she saw a strange light.", "There was a noise and then a strange light appeared.", "They were afraid because of the strange light.", 1, Question.DIFFICULT_MEDIUM, SubjectActivity.ENGLISH);
        insertQuestions(question57);
        Question question58 = new Question("Select the simple sentence which has a subject,subject,verb pattern (SSV).", "Their dog and cat have become good friends.", "Dogs provide companionship and protect their owners.", "Many people keep dogs and cats for companionship or protection.", "They want a dog or cat for a pet.", 1, Question.DIFFICULT_MEDIUM, SubjectActivity.ENGLISH);
        insertQuestions(question58);
        Question question59 = new Question("Identify the answer that is incorrect.", "Yuriko baked the cake. One of her sisters decorated it.", "One of her sisters decorated the cake, but Yuriko baked it.", "Yuriko baked it, but one of her sisters decorated the cake.", "One of her Yuriko's sisters decorated the cake, so she baked it.", 4, Question.DIFFICULT_MEDIUM, SubjectActivity.ENGLISH);
        insertQuestions(question59);
        Question question60 = new Question("\"In British Columbia, where, since 1990, thirteen rainforest valleys have been clearcut, 142 species of salmon have already become extinct.\". What does \"clearcut\" mean?", "a few trees have been cut down", "many trees have been cut down", "all the trees have been cut down", "None of these", 3, Question.DIFFICULT_HARD, SubjectActivity.ENGLISH);
        insertQuestions(question60);
        Question question61 = new Question("\"One group of activists believes that ALL cannabis should be legal.\". What does \"activists\" mean?", "people trying to change something", "people against the government", "people who smoke marijuana", "people which smoke marijuana", 1, Question.DIFFICULT_HARD, SubjectActivity.ENGLISH);
        insertQuestions(question61);
        Question question62 = new Question("Can you take this to _____ boss?", "the", "a", "an", "___(zero article)", 1, Question.DIFFICULT_HARD, SubjectActivity.ENGLISH);
        insertQuestions(question62);
        Question question63 = new Question("Did you like _____ movie I gave you?", "a", "an", "___ (zero article)", "the", 4, Question.DIFFICULT_HARD, SubjectActivity.ENGLISH);
        insertQuestions(question63);
        Question question64 = new Question("If we had not shopped for so long, _______________", "we had been home by now.", "we could have been home by now.", "we have been home by now.", "Both A, B, C", 2, Question.DIFFICULT_HARD, SubjectActivity.ENGLISH);
        insertQuestions(question64);
        Question question65 = new Question("The book was so interesting that I read it ____ a day.", "at", "on", "in", "by", 3, Question.DIFFICULT_HARD, SubjectActivity.ENGLISH);
        insertQuestions(question65);
        Question question66 = new Question("Danny quit smoking ________ the last semester.", "about", "while", "for", "during", 4, Question.DIFFICULT_HARD, SubjectActivity.ENGLISH);
        insertQuestions(question66);
        Question question67 = new Question("Peter: \"The weather is warmer than usual.\"\n" +
                "Byron: Peter said _______________.", "the weather it is warmer than usual", "the weather be warmer than usual", "that the weather warmer than usual", "the weather was warmer than usual", 4, Question.DIFFICULT_HARD, SubjectActivity.ENGLISH);
        insertQuestions(question67);
        Question question68 = new Question("Identify the one sentence which is not a comma splice, run-on, or fragment.", "Are they?", "Capital punishment is an issue which nobody has neutral feelings about, people are either in favour of it or against it.", "I wasn't really interested in the movie, I continued watching it anyway.", "I wasn't able to go to the office today I have a bad cold.", 1, Question.DIFFICULT_HARD, SubjectActivity.ENGLISH);
        insertQuestions(question68);
        Question question69= new Question("In what country can you visit Machu Picchu?", "Peru", "Columbia", "Chile", "Bolivia", 1, Question.DIFFICULT_EASY, SubjectActivity.GEOGRAPHY);
        insertQuestions(question69);
        Question question70 = new Question("Which U.S. state has the most active volcanoes?", "Hawaii", "California", "Washington", "Alaska", 3, Question.DIFFICULT_EASY, SubjectActivity.GEOGRAPHY);
        insertQuestions(question70);
        Question question71 = new Question("What country has the most coastline?", "Russia", "Canada", "USA", "China", 2, Question.DIFFICULT_EASY, SubjectActivity.GEOGRAPHY);
        insertQuestions(question71);
        Question question72 = new Question("What country is home to Kangaroo Island?", "Australia", "Japan", "Great Britain", "France", 1, Question.DIFFICULT_EASY, SubjectActivity.GEOGRAPHY);
        insertQuestions(question72);
        Question question73 = new Question("What is the tallest mountain in the world?", "Everest", "Aconcagua", "Qogir", "Kilimanjaro", 1, Question.DIFFICULT_EASY, SubjectActivity.GEOGRAPHY);
        insertQuestions(question73);
        Question question74 = new Question("Along with Spain and France, what is the other country to have both Atlantic and Mediterranean coastlines?", "Lebanon", "Syria", "Morocco", "Egypt", 3, Question.DIFFICULT_EASY, SubjectActivity.GEOGRAPHY);
        insertQuestions(question74);
        Question question75 = new Question("What is the only major city located on two continents?", "London", "Rome", "New delhi", "Istanbul", 4, Question.DIFFICULT_EASY, SubjectActivity.GEOGRAPHY);
        insertQuestions(question75);
        Question question76 = new Question("What river runs through Paris?", "Thames", "Seine", "Elbe", "Danube", 2, Question.DIFFICULT_EASY, SubjectActivity.GEOGRAPHY);
        insertQuestions(question76);
        Question question77 = new Question("What Asian country has Kuala Lumpur as its capital?", "Malaysia", "Bangladesh", "Cambodia", "Thailand", 1, Question.DIFFICULT_EASY, SubjectActivity.GEOGRAPHY);
        insertQuestions(question77);
        Question question78 = new Question("Which African nation has the most pyramids?", "Algeria", "Libya", "Sudan", "Egypt", 3, Question.DIFFICULT_MEDIUM, SubjectActivity.GEOGRAPHY);
        insertQuestions(question78);
        Question question79 = new Question("What is the highest waterfall in Europe?", "Kjelfossen", "Krimml", "Rhine", "Triberg", 2, Question.DIFFICULT_MEDIUM, SubjectActivity.GEOGRAPHY);
        insertQuestions(question79);
        Question question80 = new Question("What is the flattest continent?", "South Africa", "Antarctica", "Africa", "Australia", 3, Question.DIFFICULT_MEDIUM, SubjectActivity.GEOGRAPHY);
        insertQuestions(question80);
        Question question81 = new Question("What continent is located at Latitude 90° S Longitude 0.00° E?", "Antarctica", "Australia", "South Africa", "Asia", 1, Question.DIFFICULT_MEDIUM, SubjectActivity.GEOGRAPHY);
        insertQuestions(question81);
        Question question82 = new Question("What is the longest river in the UK?", "Tay", "Bann", "Severn", "Thames", 3, Question.DIFFICULT_MEDIUM, SubjectActivity.GEOGRAPHY);
        insertQuestions(question82);
        Question question83 = new Question("What are the only three countries completely surrounded by one other country?", "Singapore, Tonga, Saint Lucia", "Malta, Granada, Vatican", "Liechtenstein, Tuvalu, Monaco", "Lesotho, Vatican, San Marino", 4, Question.DIFFICULT_HARD, SubjectActivity.GEOGRAPHY);
        insertQuestions(question83);
        Question question84 = new Question("What U.S. state shares borders with Louisiana, Arkansas, Oklahoma, and New Mexico?", "Colorado", "Kansas", "Texas", "Missouri", 3, Question.DIFFICULT_HARD, SubjectActivity.GEOGRAPHY);
        insertQuestions(question84);
        Question question85 = new Question("What continent contains the most fresh water?", "Antarctica", "North America", "Asia", "Africa", 3, Question.DIFFICULT_HARD, SubjectActivity.GEOGRAPHY);
        insertQuestions(question85);
        Question question86 = new Question("What is the deepest point in Earth's oceans?", "Tonga Trench", "Marina Trench", "Eurasian Basin", "Java Trench", 2, Question.DIFFICULT_HARD, SubjectActivity.GEOGRAPHY);
        insertQuestions(question86);
        Question question87 = new Question("What Canadian province boasts the longest freshwater beach in the world?", "Saskatchewan", "British Columbia", "Nova Scotia", "Ontario", 4, Question.DIFFICULT_HARD, SubjectActivity.GEOGRAPHY);
        insertQuestions(question87);
        Question question88 = new Question("What Australian city boasts the world's largest natural harbor?", "Brisbane", "Sydney", "Perth", "Melbourne", 2, Question.DIFFICULT_HARD, SubjectActivity.GEOGRAPHY);
        insertQuestions(question88);
        Question question89 = new Question("Which of the following lakes is located in an ancient meteorite impact crater?", "Bosumtwi", "Poopo", "Titicaca", "Mweru", 1, Question.DIFFICULT_HARD, SubjectActivity.GEOGRAPHY);
        insertQuestions(question89);
        Question question90 = new Question("How many tributaries does the Amazon River have?", "560", "1100", "3500", "9", 2, Question.DIFFICULT_HARD, SubjectActivity.GEOGRAPHY);
        insertQuestions(question90);
    }

    public void addQuestions(Question question){
        database = getWritableDatabase();
        insertQuestions(question);
    }

    public void addQuestionMore(List<Question> questions){
        database = getWritableDatabase();
        for (Question question:questions){
            insertQuestions(question);
        }
    }
    private void insertQuestions(Question question){
        ContentValues contentValues = new ContentValues();
        contentValues.put(QuestionTable.COLUMN_QUESTION, question.getQuestion());
        contentValues.put(QuestionTable.COLUMN_OPTIONA, question.getOptionA());
        contentValues.put(QuestionTable.COLUMN_OPTIONB, question.getOptionB());
        contentValues.put(QuestionTable.COLUMN_OPTIONC, question.getOptionC());
        contentValues.put(QuestionTable.COLUMN_OPTIOND, question.getOptionD());
        contentValues.put(QuestionTable.COLUMN_ANSWERNUMBER, question.getAnswerNumber());
        contentValues.put(QuestionTable.COLUMN_DIFFICULT, question.getDifficult());
        contentValues.put(QuestionTable.COLUMN_CONNECT_ID, question.getIdConnect());
        database.insert(QuestionTable.TABLE_NAME, null, contentValues);
    }

    public List<SubjectActivity> getAllSubjects(){
        List<SubjectActivity> subjectList = new ArrayList<>();
        database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + SubjectTable.TABLE_SUBJECT, null);
        if (cursor.moveToFirst()){
            do {
                SubjectActivity subjectActivity = new SubjectActivity();
                subjectActivity.setId(cursor.getInt(cursor.getColumnIndexOrThrow(SubjectTable._ID)));
                subjectActivity.setNameSubject(cursor.getString(cursor.getColumnIndexOrThrow(SubjectTable.COLUMN_SUBJECT)));
                subjectList.add(subjectActivity);
            }while (cursor.moveToNext());
        }

        cursor.close();
        return subjectList;
    }


    public ArrayList<Question> getQuestionsAll(){
        ArrayList<Question> questionList = new ArrayList<>();
        database = getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + QuestionTable.TABLE_NAME, null);
        if (cursor.moveToFirst()){
            do{
                Question question = new Question();
                question.setId(cursor.getInt(cursor.getColumnIndexOrThrow(QuestionTable._ID)));
                question.setQuestion(cursor.getString(cursor.getColumnIndexOrThrow(QuestionTable.COLUMN_QUESTION)));
                question.setOptionA(cursor.getString(cursor.getColumnIndexOrThrow(QuestionTable.COLUMN_OPTIONA)));
                question.setOptionB(cursor.getString(cursor.getColumnIndexOrThrow(QuestionTable.COLUMN_OPTIONB)));
                question.setOptionC(cursor.getString(cursor.getColumnIndexOrThrow(QuestionTable.COLUMN_OPTIONC)));
                question.setOptionD(cursor.getString(cursor.getColumnIndexOrThrow(QuestionTable.COLUMN_OPTIOND)));
                question.setAnswerNumber(cursor.getInt(cursor.getColumnIndexOrThrow(QuestionTable.COLUMN_ANSWERNUMBER)));
                question.setDifficult(cursor.getString(cursor.getColumnIndexOrThrow(QuestionTable.COLUMN_DIFFICULT)));
                question.setIdConnect(cursor.getInt(cursor.getColumnIndexOrThrow(QuestionTable.COLUMN_CONNECT_ID)));
                questionList.add(question);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return questionList;
    }


    public ArrayList<Question> getQuestions(int idConnect, String difficult){
        ArrayList<Question> questionList = new ArrayList<>();
        database = getReadableDatabase();


        String select = QuestionTable.COLUMN_CONNECT_ID + " = ? " +
                " AND " + QuestionTable.COLUMN_DIFFICULT + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(idConnect), difficult};
        Cursor cursor = database.query(
                QuestionTable.TABLE_NAME,
                null,
                select,
                selectionArgs,
                null,
                null,
                null
        );
        if (cursor.moveToFirst()){
            do{
                Question question = new Question();
                question.setId(cursor.getInt(cursor.getColumnIndexOrThrow(QuestionTable._ID)));
                question.setQuestion(cursor.getString(cursor.getColumnIndexOrThrow(QuestionTable.COLUMN_QUESTION)));
                question.setOptionA(cursor.getString(cursor.getColumnIndexOrThrow(QuestionTable.COLUMN_OPTIONA)));
                question.setOptionB(cursor.getString(cursor.getColumnIndexOrThrow(QuestionTable.COLUMN_OPTIONB)));
                question.setOptionC(cursor.getString(cursor.getColumnIndexOrThrow(QuestionTable.COLUMN_OPTIONC)));
                question.setOptionD(cursor.getString(cursor.getColumnIndexOrThrow(QuestionTable.COLUMN_OPTIOND)));
                question.setAnswerNumber(cursor.getInt(cursor.getColumnIndexOrThrow(QuestionTable.COLUMN_ANSWERNUMBER)));
                question.setDifficult(cursor.getString(cursor.getColumnIndexOrThrow(QuestionTable.COLUMN_DIFFICULT)));
                question.setIdConnect(cursor.getInt(cursor.getColumnIndexOrThrow(QuestionTable.COLUMN_CONNECT_ID)));
                questionList.add(question);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return questionList;
    }
}
