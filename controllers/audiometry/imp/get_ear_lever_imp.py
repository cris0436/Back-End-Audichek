from controllers.audiometry.get_ear_lever import GetEarLeverController
from sqlalchemy.orm import Session
from schemas.Audiometry import Audiometry as Audiometry_shema 
import math
from models.entities.User import User


from fastapi import HTTPException, status, Depends

from models.entities.Person import Person
from datetime import datetime
from models.persistence.DatabaseSession import DataBaseSession
db_session = DataBaseSession()
class GetEarLeverImp(GetEarLeverController):
    def __init__(self, db: Session):
        self.db= db

    def exponential_function(self, x: float) -> float:
        return 6.2971 * math.exp(0.0001 * x)

    def get_ear_level_80(self, audiometry_data: Audiometry_shema, age: int) -> float:
        frecuency = 0.000004167 * (age)**4 - 0.0006833 * (age)**3 + 0.0375 * (age)**2 - 0.73492 * (age) + 20
        frecuency = frecuency * 1000  # Convertir a Hz
        frecuency_not_ear = min(
            (i.frequency for i in audiometry_data.decibel_frequencies if not i.is_ear),
            default=0
        )

        if frecuency_not_ear == 0:
            return 100  # Perfecto estado auditivo
        
        if (frecuency - (frecuency_not_ear + 2000)) < 0:
            return 100 - max(0, ((frecuency_not_ear + 2000)-frecuency  ) * 100 / frecuency)
        
        return 100 - max(0, (frecuency - (frecuency_not_ear + 2000)) * 100 / frecuency)  

    def get_ear_level(self, audiometry_data: Audiometry_shema, age: int) -> float:
        if age < 0:
            raise ValueError("Age cannot be negative")

        if age <= 80:
            return self.get_ear_level_80(audiometry_data, age)
        else:
            value = 100
            for i in audiometry_data.decibel_frequencies:
                if i.is_ear:
                    decibel_function = self.exponential_function(i.frequency)
                    deviation = max(0, decibel_function - i.decibel)  # Evitar negativos
                    value = (value + (deviation * 100 / decibel_function)) / 2  # Promedio ajustado

            return max(0, value)  # Asegurar que el score no sea negativo
                    
    
    def getAge(self, user_id: int) -> int:
        try:
            user = self.db.query(User).filter(User.id == user_id).first()
            if user is None:
                raise HTTPException(
                    status_code=status.HTTP_404_NOT_FOUND,
                    detail="User not found",
                    headers={"X-Error": "User not found"}
                )
            person= self.db.query(Person).filter(Person.id == user.person_id).first()
            if person is None:
                raise HTTPException(
                    status_code=status.HTTP_404_NOT_FOUND,
                    detail="Person not found",
                    headers={"X-Error": "Person not found"}
                )
            
            birth_date:datetime = person.birth_date
            age = datetime.now().year - birth_date.year
            return age
        except Exception as e:
            self.db.rollback()
            raise HTTPException(
                status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
                detail="Error while getting user age",
                headers={"X-Error": str(e)}
            ) 

def get_ear_lever_imp_controller(db: Session = Depends(db_session.get_db)):
    return GetEarLeverImp(db)
