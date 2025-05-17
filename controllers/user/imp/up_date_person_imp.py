from  user.up_date_person_person import UpDatePerson
from sqlalchemy.orm import Session
from models.pesistency import DataBaseSession as db
from fastapi  import Depends

class UpdatePersonImp(UpDatePerson):
    def __init__(self,db:Session = Depends(db.get_db())):
        self.db=db
        
    def updatePerson(self, personInfo):
        user = self.db.query(User).filter(User.id == user_id).first()
        if not user:
            return None
        return None
