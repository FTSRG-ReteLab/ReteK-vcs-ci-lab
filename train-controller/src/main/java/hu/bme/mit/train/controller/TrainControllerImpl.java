package hu.bme.mit.train.controller;

import java.util.Date;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;

import hu.bme.mit.train.interfaces.TrainController;

public class TrainControllerImpl implements TrainController {

	private int step = 0;
	private int referenceSpeed = 0;
	private int speedLimit = 0;
	public Table<Long, Integer, Integer> table = HashBasedTable.create();


	@Override
	public void followSpeed() {
		if (referenceSpeed < 0) {
			referenceSpeed = 0;
		} else {
			referenceSpeed += step;
			
			if (referenceSpeed < 0)
				referenceSpeed = 0;
		}

		enforceSpeedLimit();
		
		table.put(new java.util.Date().getTime(),step	, referenceSpeed);
	}

	@Override
	public int getReferenceSpeed() {
		return referenceSpeed;
	}

	@Override
	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
		enforceSpeedLimit();
		
	}

	private void enforceSpeedLimit() {
		if (referenceSpeed > speedLimit) {
			referenceSpeed = speedLimit;
		}
	}

	@Override
	public void setJoystickPosition(int joystickPosition) {
		this.step = joystickPosition;		
	}
	
	public void emergencyStop(){
		referenceSpeed=0;
	}

	@Override
	public int getFirstStep() {
		for (Cell<Long,Integer, Integer> cell: table.cellSet()){
			 return cell.getColumnKey();
			}
		return 0;
	}

}
