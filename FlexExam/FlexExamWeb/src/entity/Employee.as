package entity
{
	import entity.EmployeeStatus;
	public class Employee
	{
		public var employeeid:String;
		public var employeename:String;
		
		public var employeestatus:String;
		
		public function Employee()
		{
			employeeid = "";
			employeename = "";
			employeestatus = EmployeeStatus.DEFAULT;
		}
		
		public function get toXMLFormat():String{
			return "<employee><id>"+employeeid + "</id><name>"
				+ employeename + "</name><status>" + employeestatus
				+ "</status></employee>";
		}
		
		

	}
}