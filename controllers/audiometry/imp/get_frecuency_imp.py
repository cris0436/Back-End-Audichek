from controllers.audiometry.get_frecuency import GetFrequencyController
from models.persistence.DatabaseSession import DataBaseSession
from sqlalchemy.orm import Session
from models.entities.Frecuency import Frecuency   
from fastapi import Depends ,HTTPException, status

db_session = DataBaseSession()
class GetFrequencyControllerImp(GetFrequencyController):
    def __init__(self,db:Session):
        self.db = db
    def get_frequency(self, frecuency_value: int):
        try:
            frecuancy = self.db.query(Frecuency).filter(Frecuency.value == frecuency_value).first()
            if frecuancy is None:
                frecuancy = Frecuency(value=frecuency_value)
                self.db.add(frecuancy)
                self.db.commit()
                self.db.refresh(frecuancy)
                frecuancy = self.db.query(Frecuency).filter(Frecuency.value == frecuency_value).first()
            return frecuancy
        except Exception as e:
            self.db.rollback()
            raise HTTPException(
                status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
                detail="Error while getting frecuency",
                headers={"X-Error": str(e)}
            )
        
def get_frequency_imp_controller(db: Session = Depends(db_session.get_db)):
    return GetFrequencyControllerImp(db)
