from sqlalchemy.orm import Session
from models.entities import User
from controllers.user import GetUserController
from fastapi import Depends
from models.persistence.DatabaseSession import DataBaseSession
from models.entities.Person import Person
from schemas.UserSchema import UserOut ,PersonData
db_session= DataBaseSession()
class GetUserImp(GetUserController):
    def __init__(self ,db: Session):
        self.db= db
        
    def get_user(self, user_id: int):
        user = self.db.query(User).filter(User.id == user_id).first()
        if not user:
            return None
        return None
    
    def get_all_users(self):
        """        cedula: str = None
            name: str = None
            email: EmailStr = None
            role: str = None
            birth_date: date = None"""
        user_shema: list[UserOut] = []
        users:list[User] = self.db.query(User).all()
        for user in users:
            
            person:Person = user.person
            user_shema.append(UserOut(
                id=user.id,
                username=user.username,
                ocupation=user.ocupation,
                person=PersonData(
                    cedula=person.cedula,
                    name=person.name,
                    role="User",
                    birth_date=person.birth_date

                )
                
                )
            )

        if not users:
            return []
        return users
    
def getGetUserControllerImp( db: Session = Depends(db_session.get_db)):
    return GetUserImp(db)