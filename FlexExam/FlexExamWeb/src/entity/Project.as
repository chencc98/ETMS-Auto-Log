package entity
{
	import entity.ProjectStatus;
	public class Project
	{
		public var projectid:String;
		public var projectname:String;
		public var clientid:String;
		public var progress:Number;
		public var projectstatus:String;
		
		public function Project()
		{
			projectid = "";
			projectname = "";
			clientid = "";
			progress = 0;
			projectstatus = ProjectStatus.DEFAULT;
		}
		
		public function Project(id:String, name:String, cid:String, 
			prog:Number, status:String)
			{
				projectid = id;
				projectname = name;
				clientid = cid;
				progress = prog;
				projectstatus = status;
			}
		
		
	}
}