

from controllers.audiometry.add_audiometry import AddAudiometryController
from models.persistence.DatabaseSession import DataBaseSession
from controllers.audiometry.add_audiometry_results import AddAudiometryResults
from schemas.Audiometry import Audiometry as AudiometrySchema
from models.entities.Audiometry import Audiometry
from sqlalchemy.orm import Session
from controllers.audiometry.imp.add_audiometry_results_imp import get_add_audiometry_result

from fastapi import Depends ,HTTPException, status
from models.entities.User import User

db_connection = DataBaseSession()  

class AddAudiometryControllerImp(AddAudiometryController):
    def __init__(self, db: Session , add_audiometry_results:AddAudiometryResults):
        self.db = db
        self.add_audiometry_results = add_audiometry_results
        
    def add_audiometry(self, audiometry_data:AudiometrySchema) -> AudiometrySchema:
        try:
            audiometry:Audiometry = Audiometry(patient_id=audiometry_data.user_id)
            user:User=self.db.query(User).filter(User.id == audiometry_data.user_id).first()
            if user is None:
                raise HTTPException(
                    status_code=status.HTTP_404_NOT_FOUND,
                    detail="User not found",
                    headers={"X-Error": "User not found"}
                )
            self.db.add(audiometry)
            self.db.commit()
            self.db.refresh(audiometry)

            for i in range(len(audiometry_data.decibel_frequencies)):
                self.add_audiometry_results.add_audiometry_result(audiometry.id, audiometry_data.decibel_frequencies[i])
            return audiometry_data
        
        except Exception as e:
            self.db.rollback()
            raise HTTPException(
                status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
                detail="Error while adding audiometry",
                headers={"X-Error": str(e)}
            )
        
def getAddAudiometryController(db: Session = Depends(db_connection.get_db), 
                           add_audiometry_results: AddAudiometryResults = Depends(get_add_audiometry_result)):
    return AddAudiometryControllerImp(db, add_audiometry_results)