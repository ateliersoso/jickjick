package atelier.soso.jickjick.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import atelier.soso.jickjick.db.DBTables.ABRepeat;
import atelier.soso.jickjick.db.DBTables.SoundFile;

public class SoundDBOpenHelper {

	private static final String DATABASE_NAME = "jickjick.db";
	private static final int DATABASE_VERSION = 1;
	public static SQLiteDatabase database;
	private DatabaseHelper dbHelper;
	private Context context;

	private class DatabaseHelper extends SQLiteOpenHelper {
		public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}

		//최초 생성시 호
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DBTables.SoundFile._CREATEQUERY);
			db.execSQL(DBTables.ABRepeat._CREATEQUERY);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(DBTables.SoundFile._DELETEQUERY);
			db.execSQL(DBTables.ABRepeat._DELETEQUERY);
			
			onCreate(db);
		}
	}

	public SoundDBOpenHelper(Context context){
		this.context = context;
	}
	
	public SoundDBOpenHelper open() throws SQLException{
		dbHelper = new DatabaseHelper(context, DATABASE_NAME, null,  DATABASE_VERSION);
		database = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		database.close();
	}
	
	/**
	 * @param path
	 * @param key
	 * @return insert된 데이터의 id
	 */
	public long insertSoundFile(String path, String key){
		
		ContentValues values = new ContentValues();
		values.put(SoundFile.PATH, path);
		values.put(SoundFile.KEY,  key);

		long newRowId = 0 ;
		newRowId = database.insert(SoundFile.TABLE_NAME,  null, values);
		
		return newRowId;
	}
	

	public Cursor selectSoundFile(String fieldName, String[] fieldValue) {
		String[] projection = {
				SoundFile.ID,
				SoundFile.PATH,
				SoundFile.KEY
		};
		
		String sortOrder = SoundFile.ID + " DESC";
		
		Cursor cursor = database.query(SoundFile.TABLE_NAME, projection, fieldName, fieldValue, null, null, sortOrder);
		
		return cursor;
	}
	
	public int deleteSoundFile(String id){
		String selection = SoundFile.ID;
		String[] selectionArgs = {id};
		
		return database.delete(SoundFile.TABLE_NAME,  selection,  selectionArgs);
	}
	
	
	/**
	 * not yet implemented.
	 */
	public void updateSoundFile()
	{
		
	}
	
	public long insertABRepeat(int startMSec, int endMSec, int fileID){
		ContentValues values = new ContentValues();
		values.put(ABRepeat.START_MSEC, Integer.valueOf(startMSec));
		values.put(ABRepeat.END_MSEC, Integer.valueOf(endMSec));
		values.put(ABRepeat.SOUNDFILE_ID, Integer.valueOf(fileID));

		long newRowId = 0 ;
		newRowId = database.insert(ABRepeat.TABLE_NAME,  null, values);
		
		return newRowId;
	}
}
