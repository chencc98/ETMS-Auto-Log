package ui
{
	import flash.display.Shape;
	import flash.display.Sprite;
	import entity.ProjectStatus;

	public class StatusIcon extends Shape
	{
		private var key:String;
		
//		private static var COMPLETE:String = "COMPLETE";
//		private static var SUSPENDED:String = "SUSPENDED";
//		private static var ONGOING:String = "ONGOING";
//		
		public function StatusIcon()
		{
			//TODO: implement function
			//super();
			this.key = ProjectStatus.DEFAULT;
			drawIcon();
		}
		
		
		
		public function set Key (type:String) :void
		{
			this.key = type;
			drawIcon();
		}
		
		
		private function drawIcon():void
		{
			if( this.key == ProjectStatus.COMPLETE ){
				drawCompleteIcon();
			}else if( this.key == ProjectStatus.SUSPENDED ){
				drawSuspendedIcon();
			}else{
				drawOngoingIcon();
			}
		}
		
		private function drawCompleteIcon():void
		{
			graphics.clear();
			graphics.lineStyle(3,0x00ff00);
			graphics.moveTo(10,20);
			graphics.lineTo(25,40);
			graphics.lineTo(45,10);
		}
		
		private function drawOngoingIcon():void
		{
			graphics.clear();
			graphics.beginFill(0x0000ff);
			graphics.moveTo(5,15);
			graphics.lineTo(5,25);
			graphics.lineTo(30,25);
			graphics.lineTo(30,35);
			graphics.lineTo(45,20);
			graphics.lineTo(30,5);
			graphics.lineTo(30,15);
			graphics.lineTo(5,15);
			graphics.endFill();
		}
		
		private function drawSuspendedIcon():void
		{
			graphics.clear();
			graphics.beginFill(0xff0000);
			graphics.drawCircle(25,25,20);
			graphics.endFill();
		}
		
	}
}