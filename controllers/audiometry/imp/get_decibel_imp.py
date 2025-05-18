from controllers.audiometry.get_decibel import GetDecibelController
from models.persistence.DatabaseSession import DataBaseSession
from sqlalchemy.orm import Session
from models.entities.Decibel import Decibel
from fastapi import Depends ,HTTPException, status
db_session = DataBaseSession()
class GetDecibelControllerImp(GetDecibelController):
    def __init__(self, db: Session):
        self.db = db

    def get_decibel(self, decibel_value: int):
        try:
            decibel = self.db.query(Decibel).filter(Decibel.value == decibel_value).first()
            if decibel is None:
                decibel = Decibel(decibel_value=decibel_value)
                self.db.add(decibel)
                self.db.commit()
                self.db.refresh(decibel)
                decibel = self.db.query(Decibel).filter(Decibel.value == decibel_value).first()
            return decibel
        except Exception as e:
            self.db.rollback()
            raise HTTPException(
                status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
                detail="Error while getting decibel",
                headers={"X-Error": str(e)}
            )

    
def get_decibel_imp_controller(db: Session = Depends(db_session.get_db)):
    return GetDecibelControllerImp(db)