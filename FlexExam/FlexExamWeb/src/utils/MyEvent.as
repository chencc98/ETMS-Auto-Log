package utils
{
	import flash.events.Event;

	public class MyEvent extends Event
	{
		public static var _MY_EVENT:String = "MyEvent";
		
		public function MyEvent(type:String)
		{
			super(type);
		}
		
	}
}