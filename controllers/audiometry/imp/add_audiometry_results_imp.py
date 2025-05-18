
from controllers.audiometry.add_audiometry_results import AddAudiometryResults
from controllers.audiometry.get_audiometry import GetAudiometryController
from controllers.audiometry.get_frecuency import GetFrequencyController
from controllers.audiometry.imp.get_frecuency_imp import GetFrequencyControllerImp ,get_frequency_imp_controller
from controllers.audiometry.get_decibel import GetDecibelController
from controllers.audiometry.imp.get_decibel_imp import GetDecibelControllerImp ,get_decibel_imp_controller
from models.entities.AudiometryResults import AudiometryResults
from models.persistence.DatabaseSession import DataBaseSession
from models.entities.Decibel import Decibel
from models.entities.Frecuency import Frecuency
from schemas.Audiometry import DecibelFrequency
from sqlalchemy.orm import Session 
from fastapi import HTTPException, Depends , status

db_session = DataBaseSession()

class AddAudiometryResultsIm(AddAudiometryResults):

    def __init__(self,db:Session , get_decibel_controller:GetDecibelController, get_frecuency_controller:GetFrequencyController):
        self.db=db
        self.get_decibel_controller = get_decibel_controller
        self.get_frecuency_controller = get_frecuency_controller
    def add_audiometry_result(self,audiometryId:int,frecuencyInfo:DecibelFrequency):
        try:
            decibel:Decibel=self.get_decibel_controller.get_decibel(frecuencyInfo.decibel)
            fruency:Frecuency=self.get_frecuency_controller.get_frequency(frecuencyInfo.frequency)
            audiometry_result = AudiometryResults(
                audiometry_id=audiometryId,
                frecuency_id=fruency.id,
                decibel_id=decibel.id,
                ear=frecuencyInfo.ear,
                is_ear=frecuencyInfo.is_ear
            )
            self.db.add(audiometry_result)
            self.db.commit()
            self.db.refresh(audiometry_result)
            self.db.close()
            
        except Exception as e:
            self.db.rollback()
            raise HTTPException(
                status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
                detail="Error while adding audiometry result",
                headers={"X-Error": str(e)}
            )
def get_add_audiometry_result(db:Session =Depends(db_session.get_db) ,
                          get_decibel_controller:GetDecibelController = Depends(get_decibel_imp_controller), 
                          get_frecuency_controller:GetFrequencyController = Depends(get_frequency_imp_controller)):
    return AddAudiometryResultsIm(db,get_decibel_controller,get_frecuency_controller)
    
    