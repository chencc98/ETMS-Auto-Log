package entity
{
	public class PEMap
	{
		public var projectid:String;
		public var employeeid:String;
		
		public function PEMap()
		{
			projectid = "";
			employeeid = "";
		}
		
		public function PEMap(pid:String, eid:String)
		{
			projectid = pid;
			employeeid = eid;
		}

	}
}