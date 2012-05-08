package entity
{
	public class MainDataGridModel
	{
		public var projectid:String;
		public var projectname:String;
		public var clientname:String;
		public var progress:Number;
		public var projectstatus:String;
		public var employeenamelist:String;
		
		public function MainDataGridModel()
		{
			projectid = "";
			projectname = "";
			clientname = "";
			progress = 0;
			projectstatus = ProjectStatus.DEFAULT;
			employeenamelist = "";
		}

	}
}