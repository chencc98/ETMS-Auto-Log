package command{
    
    
    import com.adobe.cairngorm.commands.ICommand;
    import com.adobe.cairngorm.control.CairngormEvent;
    import mx.collections.ArrayCollection;
    import mx.controls.Alert;
    import mx.rpc.IResponder;
    
    import cairngormevents.AddEmployeeEvent;
    import entity.Employee;
    import model.ExamModelLocator;
    
    

    public class AddEmployeeCommand implements ICommand{
        
        public function AddEmployeeCommand()
        {
        }

        public function execute(event:CairngormEvent):void {
            var addEvent:AddEmployeeEvent = event as AddEmployeeEvent;
            var employee:Employee = addEvent.getEmployee();
            
            insertEmployee(employee);
        }
        
        
        
        private function insertEmployee(employee:Employee):void{
            var employeelist:ArrayCollection = ExamModelLocator.getInstance().projectDetailPm.employeeList;
            //search the dataprovider, if the employee exist, ignore
            var len:Number = employeelist.length;
            for( var i:Number=0; i< len; i++){
                if( employeelist[i].id == employee.id ){
                    Alert.show("Existing Employee [" + employee.name 
                            + "] detected, ignore!");
                    return;
                }
            }

            //add this employee data into data provider
            
            employeelist.addItem(employee);

        }
        
    }
}