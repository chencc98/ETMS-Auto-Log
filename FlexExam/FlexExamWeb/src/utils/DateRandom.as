package utils{
    public class DateRandom{
        public function DateRandom(){
        }


        public static function generate():String{
            var date:Date = new Date();
            var ran:Number = Math.round(Math.random() * 1000);
            return "" + date.valueOf() + ran;
        }
    }
}