package atelier.soso.jickjick.db;

import android.provider.BaseColumns;

public final class DBTables {

	public DBTables(){}
	
	public static abstract class SoundFile implements BaseColumns {
		public static final String TABLE_NAME = "SoundFile";
		public static final String ID = "id";
		public static final String PATH = "path";
		public static final String KEY = "key";
		
		public static final String _CREATEQUERY = 
				"create table " + TABLE_NAME +" ("
				+ ID			+ " integer primary key autoincrement, "
				+ PATH 			+ " text not null , "
				+ KEY 			+ " text not null); ";
		public static final String _DELETEQUERY = 
				"DROP TABLE IF EXISTS " + TABLE_NAME;
	}
	//구간 반
	public static abstract class ABRepeat implements BaseColumns {
		public static final String TABLE_NAME = "ABRepeat";
		public static final String ID = "id";
		public static final String SOUNDFILE_ID = "soundfileid";
		public static final String START_MSEC = "startmsec";
		public static final String END_MSEC = "endmsec";
		
		public static final String _CREATEQUERY = 
				"create table " + TABLE_NAME +" ("
				+ ID			+ " integer primary key autoincrement, "
				+ SOUNDFILE_ID 	+ " integer not null , "
				+ END_MSEC 		+ " integer not null , "
				+ START_MSEC 	+ " integer not null );";
		public static final String _DELETEQUERY = 
				"DROP TABLE IF EXISTS " + TABLE_NAME;
	}
}
