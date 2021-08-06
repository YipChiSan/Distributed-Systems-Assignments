
import java.sql.SQLIntegrityConstraintViolationException;

public class DictOperation {
	private String word;
	private String meaning;
	private String type;
	
	
	public DictOperation(String type,String word, String meaning){
		this.type = type;
		this.word = word.toLowerCase();
		this.meaning = meaning;
	}
	
	public DictOperation(String type,String word){
		this.type = type;
		this.word = word.toLowerCase();
		
	}
	
	
	public String run(){
		MySQLAccess temp = new MySQLAccess();
		String result = "There are errors on the connection between database and server.";
		switch(this.type){
		case "add":
			try {
				temp.writeDataBase(word,meaning);
				result = word + " has been added successfully";
			} catch (SQLIntegrityConstraintViolationException e) {
				result = "Word already exists in the database";
				return result;
			} catch (Exception e){
				e.printStackTrace();
				return e.toString();
			}
		    break;
		case "del":
			try {
				result = temp.removeDataBase(word);
				if (!result.equals("Success")){
					return result;
				}
				result = word + " has been deleted successfully";
				
			} catch (Exception e) {
				// override
				e.printStackTrace();
				return result;
			}
			break;
		case "query":
			try {
				result = temp.readDataBase(word);
			} catch (Exception e) {
				e.printStackTrace();
				return result;	
			}
			break;
		default:
			result = "Wrong Operation";
			break;
		}
		return result;
	}
}
