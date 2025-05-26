from controllers.audiometry.get_audiometry import GetAudiometryController
from sqlalchemy.orm import Session
from schemas.Audiometry import Audiometry as Audiometry_shema , DecibelFrequency
import math
from models.entities.User import User
from controllers.audiometry.get_decibel import GetDecibelController
from controllers.audiometry.imp.get_decibel_imp import GetDecibelControllerImp, get_decibel_imp_controller
from controllers.audiometry.get_frecuency import GetFrequencyController
from controllers.audiometry.imp.get_frecuency_imp import GetFrequencyControllerImp ,get_frequency_imp_controller
from controllers.audiometry.get_ear_lever import GetEarLeverController
from controllers.audiometry.imp.get_ear_lever_imp import GetEarLeverImp ,get_ear_lever_imp_controller
from models.entities.AudiometryResults import AudiometryResults
from fastapi import HTTPException, status, Depends
from models.persistence.DatabaseSession import DataBaseSession
from models.entities.Audiometry import Audiometry
from models.entities.Person import Person
from datetime import datetime
db_session = DataBaseSession()
class GetAudiometryControllerImp(GetAudiometryController):

    def __init__(self, db: Session ,
                 get_decibel_controller:GetDecibelControllerImp ,
                 get_frecuency_controller:GetFrequencyControllerImp,
                 get_ear_lever_controller:GetEarLeverController):
        self.get_frecuency_controller = get_frecuency_controller 
        self.get_decibel_controller = get_decibel_controller
        self.db=db
        self.get_ear_lever_controller = get_ear_lever_controller

    def getAllAudiometries(self) -> list[Audiometry_shema]:
        try:
            audiometries = self.db.query(Audiometry).all()
            audiometries_shema: list[Audiometry_shema] = []

            for i in audiometries:
                id = i.id  # Corrección: Asignación sin coma
                audiometry_results = self.db.query(AudiometryResults).filter(AudiometryResults.audiometry_id == id).all()

                if audiometry_results:  # Validar que haya resultados antes de agregar
                    audiometries_shema.append(AudiometryResults(
                        user_id=i.patient_id,  # Cambio id por patient_id si almacena el usuario
                        decibel_frequencies=audiometry_results
                    ))

            if not audiometries_shema:  # Validar la lista corregida, no audiometries
                raise HTTPException(
                    status_code=status.HTTP_404_NOT_FOUND,
                    detail="No audiometries found",
                    headers={"X-Error": "No audiometries found"}
                )
        
            return audiometries_shema
        except Exception as e:
            raise HTTPException(
                status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
                detail="Error while getting audiometries",
                headers={"X-Error": str(e)}
            )
    
    def getAudiometryByUserId(self, user_id: int) -> list:
        try:
            user = self.db.query(User).filter(User.id == user_id).first()
            if user is None:
                raise HTTPException(
                    status_code=status.HTTP_404_NOT_FOUND,
                    detail="User not found",
                    headers={"X-Error": "User not found"}
                )
            audiometry = self.db.query(Audiometry).filter(Audiometry.patient_id==user_id).first()
            
            if audiometry is None:
                raise HTTPException(
                    status_code=status.HTTP_404_NOT_FOUND,
                    detail="No audiometries found",
                    headers={"X-Error": "No audiometries found"}
                )
            
            
            audiometrie_results = self.db.query(AudiometryResults).filter(AudiometryResults.audiometry_id == audiometry.id).all()
            
            if audiometrie_results is None:
                raise HTTPException(
                    status_code=status.HTTP_404_NOT_FOUND,
                    detail="No audiometries found",
                    headers={"X-Error": "No audiometries found"}
                )
            
            audiometry_results_schema = [DecibelFrequency(
                decibel=self.get_decibel_controller.get_decibel_by_id(x.decibel_id).value,
                frequency=self.get_frecuency_controller.get_frecuency_by_id(x.frecuency_id).value,
                ear=x.ear,
                is_ear=x.is_ear) for x in audiometrie_results]
            
            audiometri_shema = Audiometry_shema(
                user_id=user_id,
                decibel_frequencies=audiometry_results_schema
            )
            left_ear_decibel = [self.get_decibel_controller.get_decibel_by_id(x.decibel_id).value for x in audiometrie_results if x.is_ear and x.ear == False ]
            right_ear_decibel = [self.get_decibel_controller.get_decibel_by_id(x.decibel_id).value for x in audiometrie_results if x.is_ear and x.ear == True ]
            right_ear_frequency = [self.get_frecuency_controller.get_frecuency_by_id(x.frecuency_id).value for x in audiometrie_results if x.is_ear and x.ear == True ]
        
            ear_level = self.get_ear_lever_controller.get_ear_level(audiometri_shema,self.get_ear_lever_controller.getAge(user_id))
            return [left_ear_decibel, right_ear_decibel, right_ear_frequency,[ear_level]]
        
        except Exception as e:
            self.db.rollback()
            raise HTTPException(
                status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
                detail="Error while getting audiometries",
                headers={"X-Error": str(e)}
            )

    
def get_audiometry_imp_controller(db: Session = Depends(db_session.get_db),
                                   get_decibel_controller:GetDecibelController = Depends(get_decibel_imp_controller),
                                   get_frecuency_controller:GetFrequencyController = Depends(get_frequency_imp_controller),
                                   get_ear_lever_controller:GetEarLeverController = Depends(get_ear_lever_imp_controller)):
    return GetAudiometryControllerImp(db,get_decibel_controller,get_frecuency_controller,get_ear_lever_controller)