
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class MySQLAccess {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
    public MySQLAccess(){
    	
    }

    public void writeDataBase(String word, String meaning) throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Setup the connection with the DB
            String url = "jdbc:mysql://localhost:3306/?user=" + "" +"&serverTimezone=Australia/Sydney";
            connect = DriverManager
                    .getConnection(url);
            preparedStatement = connect
                    .prepareStatement("insert into  dictionary.mainDictionary values (?, ?)");
            // "word, meaning from dictionary.mainDictionary");
            // Parameters start with 1
            preparedStatement.setString(1, word);
            preparedStatement.setString(2, meaning);    
            preparedStatement.executeUpdate();
            System.out.println(word + " has been added successfully");
        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }

    public String readDataBase(String word) throws Exception{
    	try{
    		Class.forName("com.mysql.cj.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/?user=" + "" +"&serverTimezone=Australia/Sydney");
                  
            preparedStatement = connect
                    .prepareStatement("select * from dictionary.mainDictionary where word= ?");
            preparedStatement.setString(1, word);
            resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()){
            	String result = resultSet.getString("meaning");
            	return result;
            } else{
            	return "Cannot find the meaning of " + word;
            }
            
    	} catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }
    
    public String removeDataBase(String word) throws Exception{
    	String result = "Cannot find the meaning of " + word;
    	
    	if (readDataBase(word).equals(result)){
    		return result;
    	}
    	try{
    		Class.forName("com.mysql.cj.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/?user=" + "" +"&serverTimezone=Australia/Sydney");
                  
            preparedStatement = connect
                    .prepareStatement("delete from dictionary.mainDictionary where word=?");
            preparedStatement.setString(1, word);
            preparedStatement.executeUpdate();
            System.out.println(word + " removed");
            return "Success";
    	} catch (Exception e) {
            throw e;
        } finally {
            close();
        }
    }
   
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }

}
