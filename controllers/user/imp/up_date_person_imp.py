from controllers.user.up_date_person_person import UpDatePerson
from sqlalchemy.orm import Session
from models.persistence import DataBaseSession 
from fastapi  import Depends, HTTPException, status
from schemas import UserCreate
from models.entities import User
from models.entities import Person
from models.entities import Rol

db_session= DataBaseSession()
class UpdatePersonImp(UpDatePerson):
    def __init__(self,db:Session):
        self.db=db
        
    def updatePerson(self, user_id:str, updated_data: UserCreate):
        try:
            user = self.db.query(User).filter(User.id == user_id).first()
            if not user:
                return None
            person = self.db.query(Person).filter(Person.id == user.person_id).first()
            if not person:
                return None
            rol = self.db.query(Rol).filter(Rol.name == updated_data.rol).first()
            if not rol:
                return None
            
            person.cedula = updated_data.cedula
            person.name = updated_data.name
            person.email = updated_data.email
            person.birth_date = updated_data.birth_date
            person.rol_id = rol.id
            user.ocupation = updated_data.ocupation
            user.password = updated_data.password
            user.username = updated_data.username

            self.db.commit()
            self.db.refresh(user)
            self.db.refresh(person)
            return user
        except Exception as e:
            self.db.rollback()
            raise HTTPException(
                status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
                detail="Error while updating person",
                headers={"X-Error": str(e)}
            )

def getUpdatePersonControllerImp(db: Session = Depends(db_session.get_db)):
    return UpdatePersonImp(db)
