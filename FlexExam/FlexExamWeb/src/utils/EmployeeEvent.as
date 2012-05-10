package utils
{
	import entity.Employee;
	
	import flash.events.Event;

	public class EmployeeEvent extends Event
	{
		private var em:Employee;
		
		public function EmployeeEvent(type:String, emp:Employee)
		{
			super(type);
			em = emp;
		}
		
		public function get Employ() :Employee
		{
			return em;
		}
		
	}
}