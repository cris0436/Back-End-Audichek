from controllers.audiometry.get_audiometry import GetAudiometryController
class GetAudiometryControllerImp(GetAudiometryController):
    def __init__(self, audiometry_service):
        self.audiometry_service = audiometry_service
    def get_audiometryResult(self, audiometry_id: str):
        pass
    def getAllAudiometries(self):
        pass
    def getAudiometryByUserId(self, user_id: str):
        pass
    
