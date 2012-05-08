package entity
{
	public class Client
	{
		public var clientid:String;
		public var clientname:String;
		
		public function Client()
		{
			clientid = "";
			clientname = "";
		}
		public function Client(id:String, name:String){
			clientid = id;
			clientname = name;
		}
		
		

	}
}