package project_1_assignment;

import java.sql.*;
import java.util.ArrayList;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase; 
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.bson.Document;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class project_one 
{
	public static void main(String[] args) 
	{
		try
		{
			MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
			DB db = mongoClient.getDB("jmdb");
			System.out.println("Connect to database successfully");
			DBCollection movies = db.getCollection("movies");
			Class.forName("com.mysql.jdbc.Driver"); 
			String connectionURL = "jdbc:mysql://localhost:3306/jmdb?autoReconnect=true&useSSL=false";
			Connection con= DriverManager.getConnection(connectionURL,"root","mysql");  
			Statement stmt=con.createStatement();
			
			//Movies
			ArrayList movieid = new ArrayList();
			ArrayList title = new ArrayList();
			ArrayList imdbid = new ArrayList();
			ArrayList year = new ArrayList();
			ResultSet moviesrs =stmt.executeQuery("select * from movies LIMIT 1000");
			while(moviesrs.next())
				{
				movieid.add(moviesrs.getString(1));
				title.add(moviesrs.getString(2));
				year.add(moviesrs.getString(3));
				imdbid.add(moviesrs.getString(4));
				}
			
			//Language	
			ArrayList langmovieid = new ArrayList();
			ArrayList language = new ArrayList();
			ArrayList addition = new ArrayList();
			
			ResultSet languagers = stmt.executeQuery("select * from language LIMIT 1000");
			while(languagers.next())
			{
				langmovieid.add(languagers.getString(1));
				language.add(languagers.getString(2));
				addition.add(languagers.getString(3));
			}
			for(int i=0;i<langmovieid.size();i++)
				{
				for(int j=0;j<movieid.size();j++)
					{
					if(langmovieid.get(i).equals(movieid.get(j)))
						{
						BasicDBObject newDocument = new BasicDBObject("movieid",movieid.get(j)).append("title",title.get(j))
							.append("Year",year.get(j))
							.append("IMDB ID",imdbid.get(j))
							.append("Language", language.get(i))
							.append("Addition", addition.get(i));
						movies.insert(newDocument);
						}
					}
				}
			
			//Countries
			ArrayList country = new ArrayList();
			ArrayList countrymovieid = new ArrayList();
			ResultSet countriesrs = stmt.executeQuery("select * from countries LIMIT 1000");  

			while(countriesrs.next())
			{
				countrymovieid.add(countriesrs.getString(1));
				country.add(countriesrs.getString(2));
			}
			//adding country table
			for(int i=0;i<countrymovieid.size();i++)
				{
				for(int j=0;j<movieid.size();j++)
					{
					if(countrymovieid.get(i).equals(movieid.get(j)))
						{
						BasicDBObject newDocument = new BasicDBObject();
						newDocument.append("$set", new BasicDBObject().append("Country", country.get(i)));
						BasicDBObject searchQuery = new BasicDBObject().append("movieid", movieid.get(j));
						movies.update(searchQuery, newDocument);
						}
					}
				}
			
			//genres
			ArrayList genresMovieID = new ArrayList();
			ArrayList genres = new ArrayList();
			ResultSet genresRS = stmt.executeQuery("select * from genres LIMIT 1000");  

			while(genresRS.next())
			{
				genresMovieID.add(genresRS.getString(1));
				genres.add(genresRS.getString(2));
			}	
			//adding genres table
			for(int i=0;i<genresMovieID.size();i++)
				{
				for(int j=0;j<movieid.size();j++)
					{
					if(genresMovieID.get(i).equals(movieid.get(j)))
					{
						BasicDBObject newDocument = new BasicDBObject();
						newDocument.append("$set", new BasicDBObject().append("Genres", genres.get(i)));
						BasicDBObject searchQuery = new BasicDBObject().append("movieid", movieid.get(j));
						movies.update(searchQuery, newDocument);
					}
					}
				}
			
		//color
		ArrayList colorMovieID = new ArrayList();
		ArrayList color = new ArrayList();
		ResultSet colorRS = stmt.executeQuery("select * from colorinfo LIMIT 1000"); 
		while(colorRS.next())
		{
			colorMovieID.add(colorRS.getString(1));
			color.add(colorRS.getString(2));
		}
		//color table
		for(int i=0;i<colorMovieID.size();i++)
			{
			for(int j=0;j<movieid.size();j++)
			{
				if(colorMovieID.get(i).equals(movieid.get(j)))
					{
					BasicDBObject newDocument = new BasicDBObject();
					newDocument.append("$set", new BasicDBObject().append("Color", color.get(i)));
					BasicDBObject searchQuery = new BasicDBObject().append("movieid", movieid.get(j));
					movies.update(searchQuery, newDocument);
					}
				}
			}
		
		//plot
		ArrayList plotID = new ArrayList();
		ArrayList plottext = new ArrayList();
		ResultSet plotRS = stmt.executeQuery("select * from plots LIMIT 1000"); 
		while(plotRS.next())
		{
			plotID.add(plotRS.getString(1));
			plottext.add(plotRS.getString(2));
		}
		//plot table
		for(int i=0;i<plotID.size();i++)
		{
			for(int j=0;j<movieid.size();j++)
			{
				if(plotID.get(i).equals(movieid.get(j)))
				{
					BasicDBObject newDocument = new BasicDBObject();
					newDocument.append("$set", new BasicDBObject().append("Plot Text", plottext.get(i)));
					BasicDBObject searchQuery = new BasicDBObject().append("movieid", movieid.get(j));
					movies.update(searchQuery, newDocument);
				}
			}
		}
		
		//tagline
		ArrayList tagID = new ArrayList();
		ArrayList taglinetext = new ArrayList();
		ResultSet taglineRS = stmt.executeQuery("select * from taglines LIMIT 1000"); 
		while(taglineRS.next())
		{
			tagID.add(taglineRS.getString(1));
			taglinetext.add(taglineRS.getString(2));
		}
		
		//tagline table
		for(int i=0;i<tagID.size();i++)
		{
			for(int j=0;j<movieid.size();j++)
			{
				if(tagID.get(i).equals(movieid.get(j)))
				{
					BasicDBObject newDocument = new BasicDBObject();
					newDocument.append("$set", new BasicDBObject().append("Tagline Text", taglinetext.get(i)));
					BasicDBObject searchQuery = new BasicDBObject().append("movieid", movieid.get(j));
					movies.update(searchQuery, newDocument);
				}
			}
		}
		
		//movies to actors
		ArrayList m2aMovieID = new ArrayList();
		ArrayList actorid = new ArrayList();
		ArrayList actorname = new ArrayList();
		ArrayList actorascharacter = new ArrayList();
		ArrayList actorgender = new ArrayList();
 
		ResultSet m2a=stmt.executeQuery("select m.movieid,m.actorid,m.as_character,a.sex,a.name from actors as a inner join movies2actors as m on a.actorid=m.actorid order by m.movieid asc limit 1000");
		while(m2a.next())
		{
			m2aMovieID.add(m2a.getString(1));
			actorid.add(m2a.getString(2));
			actorascharacter.add(m2a.getString(3));
			actorgender.add(m2a.getString(4));
			actorname.add(m2a.getString(5));
		}
		//movie to actor table
		for(int i=0;i<m2aMovieID.size();i++)
		{
			for(int j=0;j<movieid.size();j++)
			{
				if(m2aMovieID.get(i).equals(movieid.get(j)))
				{
					BasicDBObject newDocument = new BasicDBObject();
					BasicDBObject update = new BasicDBObject();
					BasicDBObject query = new BasicDBObject();
					query.put( "movieid",movieid.get(j) );
					newDocument.put("Actor ID", actorid.get(i));
					newDocument.put("Actor name",actorname.get(i));
					newDocument.put("Gender", actorgender.get(i));
					newDocument.put("as_character", actorascharacter.get(i));
					update.put("$push", new BasicDBObject("Actors",newDocument));
					movies.update(query, update,true,true);
				}
			}
		}
		

		//movies to cinematography
		
	      ArrayList m2cMovieID = new ArrayList();
	      ArrayList cinematid = new ArrayList();
	      ArrayList m2caddition = new ArrayList();
	      ArrayList m2cname = new ArrayList();
	      ResultSet m2c=stmt.executeQuery("select m.movieid,c.cinematid,m.addition,c.name from cinematgrs as c inner join movies2cinematgrs as m on c.cinematid=m.cinematid  order by m.movieid asc limit 100");
	      while(m2c.next())
	      {
	    	  m2cMovieID.add(m2c.getString(1));
	    	  cinematid.add(m2c.getString(2));
	    	  m2caddition.add(m2c.getString(3));
	    	  m2cname.add(m2c.getString(4));
	      }
	      for(int i=0;i<m2cMovieID.size();i++)
			{
	    	  for(int j=0;j<movieid.size();j++)
	    	  {
	    		  if(m2cMovieID.get(i).equals(movieid.get(j)))
	    		  {
	    			  BasicDBObject newDocument = new BasicDBObject();
	    			  BasicDBObject update = new BasicDBObject();
	    			  BasicDBObject query = new BasicDBObject();
	    			  query.put( "movieid",movieid.get(j) );
	    			  newDocument.put("Cinematography ID", cinematid.get(i));
	    			  newDocument.put("Addition",m2caddition.get(i));
	    			  newDocument.put("Name", m2cname.get(i));
	    			  update.put("$push", new BasicDBObject("Cinematography",newDocument));
	    			  movies.update(query, update,true,true);
	    		  }
	    	  }
			}
	      //movies to composer
	      ArrayList composerid = new ArrayList();
		  ArrayList m2composermovieid = new ArrayList();
		  ArrayList m2composeraddition = new ArrayList();
		  ArrayList m2composername = new ArrayList();
		  ResultSet m2composer=stmt.executeQuery("select c.composerid,m.movieid,m.addition,c.name from composers as c inner join movies2composers as m on c.composerid = m.composerid order by m.movieid asc limit 100");
		  while(m2composer.next())
		  {
			  composerid.add(m2composer.getString(1));
			  m2composermovieid.add(m2composer.getString(2));
			  m2composeraddition.add(m2composer.getString(3));
			  m2composername.add(m2composer.getString(4));
		  }
		  
		  for(int i=0;i<m2composermovieid.size();i++)
		  {
			  for(int j=0;j<movieid.size();j++)
			  {
				  if(m2composermovieid.get(i).equals(movieid.get(j)))
				  {
					  BasicDBObject newDocument = new BasicDBObject();
					  BasicDBObject update = new BasicDBObject();
					  BasicDBObject query = new BasicDBObject();
					  query.put( "movieid",movieid.get(j) );
					  newDocument.put("Composer ID", composerid.get(i));
					  newDocument.put("Addition",m2composeraddition.get(i));
					  newDocument.put("Name", m2composername.get(i));
					  update.put("$push", new BasicDBObject("Composer",newDocument));
					  movies.update(query, update,true,true);
				  }
			  }
		  }
		  //movies to director  
		  ArrayList Directorid = new ArrayList();
		  ArrayList m2director_movieid = new ArrayList();
		  ArrayList m2director_addition = new ArrayList();
		  ArrayList m2director_name = new ArrayList();
		  ResultSet m2director=stmt.executeQuery("select d.directorid,m.movieid,m.addition,d.name from directors as d inner join movies2directors as m on d.directorid = m.directorid order by m.movieid asc limit 100");
		  while(m2director.next())
		  {
			  Directorid.add(m2director.getString(1));
			  m2director_movieid.add(m2director.getString(2));
			  m2director_addition.add(m2director.getString(3));
			  m2director_name.add(m2director.getString(4));
		  }
		  for(int i=0;i<m2director_movieid.size();i++)
		  {
			  for(int j=0;j<movieid.size();j++)
			  {
				  if(m2director_movieid.get(i).equals(movieid.get(j)))
				  {
					  BasicDBObject newDocument = new BasicDBObject();
					  BasicDBObject update = new BasicDBObject();
					  BasicDBObject query = new BasicDBObject();
					  query.put( "movieid",movieid.get(j) );
					  newDocument.put("Director ID", Directorid.get(i));
					  newDocument.put("Addition",m2director_addition.get(i));
					  newDocument.put("Name", m2director_name.get(i));
					  update.put("$push", new BasicDBObject("Director",newDocument));
					  movies.update(query, update,true,true);
				  }
			  }
		  }
		  //movie to editor 
		  ArrayList Editorid = new ArrayList();
		  ArrayList m2editor_movieid = new ArrayList();
		  ArrayList m2editor_addition = new ArrayList();
		  ArrayList m2editor_name = new ArrayList();
		  ResultSet m2editor=stmt.executeQuery("select e.editorid,m.movieid,m.addition,e.name from editors as e inner join movies2editors as m on e.editorid = m.editorid order by m.movieid asc limit 100");
		  while(m2editor.next())
		  {
			  Editorid.add(m2editor.getString(1));
			  m2editor_movieid.add(m2editor.getString(2));
			  m2editor_addition.add(m2editor.getString(3));
			  m2editor_name.add(m2editor.getString(4));
		  }
		  for(int i=0;i<m2editor_movieid.size();i++)
		  {
			  for(int j=0;j<movieid.size();j++)
			  {
				  if(m2editor_movieid.get(i).equals(movieid.get(j)))
				  {
					  BasicDBObject newDocument = new BasicDBObject();
					  BasicDBObject update = new BasicDBObject();
					  BasicDBObject query = new BasicDBObject();
					  query.put( "movieid",movieid.get(j) );
					  newDocument.put("Editor ID", Editorid.get(i));
					  newDocument.put("Addition",m2editor_addition.get(i));
					  newDocument.put("Name", m2editor_name.get(i));
					  update.put("$push", new BasicDBObject("Editor",newDocument));
					  movies.update(query, update,true,true);
				  }
			  }
		  }
		  
		  //movie to costume designer
		  ArrayList costdesid = new ArrayList();
		  ArrayList m2costdes_movieid = new ArrayList();
		  ArrayList m2cosdes_addition = new ArrayList();
		  ArrayList m2costdes_name = new ArrayList();
		  ResultSet m2costdesrs=stmt.executeQuery("select c.costdesid,m.movieid,m.addition,c.name from costdesigners as c inner join movies2costdes as m on c.costdesid = m.costdesid order by m.movieid asc limit 100");
		  while(m2costdesrs.next())
		  {
			  costdesid.add(m2costdesrs.getString(1));
			  m2costdes_movieid.add(m2costdesrs.getString(2));
			  m2cosdes_addition.add(m2costdesrs.getString(3));
			  m2costdes_name.add(m2costdesrs.getString(4));
		  }
		  for(int i=0;i<m2costdes_movieid.size();i++)
		  {
			  for(int j=0;j<movieid.size();j++)
			  {
				  if(m2costdes_movieid.get(i).equals(movieid.get(j)))
				  {
					  BasicDBObject newDocument = new BasicDBObject();
					  BasicDBObject update = new BasicDBObject();
					  BasicDBObject query = new BasicDBObject();
					  query.put( "movieid",movieid.get(j) );
					  newDocument.put("Costume Designer ID", costdesid.get(i));
					  newDocument.put("Addition",m2cosdes_addition.get(i));
					  newDocument.put("Name", m2costdes_name.get(i));
					  update.put("$push", new BasicDBObject("Costume Designer",newDocument));
					  movies.update(query, update,true,true);
				  }
			  }
		  }
		  //movie to producer
		  ArrayList Producerid = new ArrayList();
		  ArrayList m2producer_movieid = new ArrayList();
		  ArrayList m2producer_addition = new ArrayList();
		  ArrayList m2producer_name = new ArrayList();
		  ResultSet m2producer=stmt.executeQuery("select p.producerid,m.movieid,m.addition,p.name from producers as p inner join movies2producers as m on p.producerid = m.producerid order by m.movieid asc limit 100");
		while(m2producer.next())
		{
			Producerid.add(m2producer.getString(1));
			m2producer_movieid.add(m2producer.getString(2));
			m2producer_addition.add(m2producer.getString(3));
			m2producer_name.add(m2producer.getString(4));
		}
		for(int i=0;i<m2producer_movieid.size();i++)
		{
			for(int j=0;j<movieid.size();j++)
			{
				if(m2producer_movieid.get(i).equals(movieid.get(j)))
				{
					BasicDBObject newDocument = new BasicDBObject();
					BasicDBObject update = new BasicDBObject();
					BasicDBObject query = new BasicDBObject();
					query.put( "movieid",movieid.get(j) );
					newDocument.put("Producer ID", Producerid.get(i));
					newDocument.put("Addition",m2producer_addition.get(i));
					newDocument.put("Name", m2producer_name.get(i));
					update.put("$push", new BasicDBObject("Producer",newDocument));
					movies.update(query, update,true,true);
				}
			}
		}
		//movie to writer
		ArrayList Writerid = new ArrayList();
		ArrayList m2writer_movieid = new ArrayList();
		ArrayList m2writer_addition = new ArrayList();
		ArrayList m2writer_name = new ArrayList();
		ResultSet m2writer=stmt.executeQuery("select w.writerid,m.movieid,m.addition,w.name from writers as w inner join movies2writers as m on w.writerid = m.writerid order by m.movieid asc limit 100");
		while(m2writer.next())
		{
			Writerid.add(m2writer.getString(1));
			m2writer_movieid.add(m2writer.getString(2));
			m2writer_addition.add(m2writer.getString(3));
			m2writer_name.add(m2writer.getString(4));
		}
		for(int i=0;i<m2writer_movieid.size();i++)
		{
			for(int j=0;j<movieid.size();j++)
			{
				if(m2writer_movieid.get(i).equals(movieid.get(j)))
				{
					BasicDBObject newDocument = new BasicDBObject();
					BasicDBObject update = new BasicDBObject();
					BasicDBObject query = new BasicDBObject();
					query.put( "movieid",movieid.get(j) );
					newDocument.put("Writer ID", Writerid.get(i));
					newDocument.put("Addition",m2writer_addition.get(i));
					newDocument.put("Name", m2writer_name.get(i));
					update.put("$push", new BasicDBObject("Writer",newDocument));
					movies.update(query, update,true,true);
				}
			}
		}
		DBCursor cursor = movies.find();
		while (cursor.hasNext()) 
		{ 
			DBObject doc = cursor.next();
	        System.out.println(doc);
		}
		cursor.close();
		con.close();
	}
		catch(Exception e)
		{ 
			System.out.println(e);
		}
	}
}
