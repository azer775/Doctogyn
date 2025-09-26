export enum HormoneStatus {
      Prepuberty= 'Prepuberty',
      Reproductive_period= 'Reproductive_period', 
      Perimenopause= 'Perimenopause', 
      Menopause= 'Menopause', 
      Undefined= 'Undefined'

}

export enum ConsultationType {
  GYNECOLOGY = 'Gynecology',
  OBSTETRICS = 'Obstetrics',
  FERTILITY = 'Fertility'
}
export enum Status{
  Normal= 'Normal',
  Abnormal= 'Abnormal',
}

export enum CivilState {
  Single = 'Single',
  Married = 'Married',
  Divorced = 'Divorced',
  Widowed = 'Widowed',
  Cohabiting = 'Cohabiting'
}

export enum Size {
  SMALL = 'SMALL',
  MEDIUM = 'MEDIUM',
  LARGE = 'LARGE'
  // Placeholder; Java enum was empty, please provide actual values
}


export enum Sex {
  Male = 'Male',
  Female = 'Female'
}

export enum ConceptionType {
  Natural = 'Natural',
  OvulationStimulation = 'OvulationStimulation',
  IVF = 'IVF',
  ArtificialInsemination = 'ArtificialInsemination'
}

export enum FamilialPathology {
  OVARIAN_CANCER = 'OvarianCancer',
  PREMATURE_MENOPAUSE = 'PrematureMenopause',
  DIABETES = 'Diabetes',
  ENDOMETRIAL_CANCER = 'EndometrialCancer',
  COLON_CANCER = 'ColonCancer'
}

export enum Allergies {
  Drug = 'Drug',
  Food = 'Food',
  Skin = 'Skin',
  Respiratory = 'Respiratory'
}

export enum MedicalPathology {
  HeartDisease = 'HeartDisease',
  BoneCancer = 'BoneCancer',
  LiverDisease = 'LiverDisease',
  HepatitisB = 'HepatitisB',
  Depression = 'Depression'
}

export enum ChirurgicalPathology {
  APPENDECTOMY = 'Appendectomy',
  HERNIA = 'Hernia',
  HYSTERECTOMY = 'Hysterectomy',
  LAPAROTOMY = 'Laparotomy',
  LAPAROSCOPY = 'Laparoscopy',
  OTHER = 'Other'
}

export enum Duration {
  SHORT = 'SHORT',
  MEDIUM = 'MEDIUM',
  LONG = 'LONG'
  // Placeholder; Java enum was empty, please provide actual values
}
export enum BackgroundType {
    Familial = 'Familial',
    Medical = 'Medical',
    Chirurgical = 'Chirurgical',
    Allergies = 'Allergies'
}
export enum UterusSize {
    Normal = 'Normal',
    Increased = 'Increased',
    Hypoplasia = 'Hypoplasia'
}

export enum Pelvicdiagnosticpresumption {
    Digestivemass = 'Digestivemass',
    Ectopicpregnancy = 'Ectopicpregnancy',
    Hydrosalpinx = 'Hydrosalpinx',
    Paratubalcyst = 'Paratubalcyst',
    Falsecyst = 'Falsecyst',
    Unidentifiedorigin = 'Unidentifiedorigin',
    Subserosalmyoma = 'Subserosalmyoma'
}

export enum Ovary {
    Normal = 'Normal',
    Unexamend = 'Unexamend',
    Cyste = 'Cyste',
    Follicule = 'Follicule',
    SOPK = 'SOPK'
}

export enum Myometre {
    Normal = 'Normal',
    Myome = 'Myome',
    Adenomyose = 'Adenomyose'
}

export enum Diagnosticpresumption {
    Dermoidcyst = 'Dermoidcyst',
    Endometrioticcyst = 'Endometrioticcyst',
    Functionalcyst = 'Functionalcyst',
    Unidentified_presumed_benign = 'Unidentified_presumed_benign',
    Unidentified_presumed_malignant = 'Unidentified_presumed_malignant'
}
export enum ResponseType {
    SUMMARY = 'SUMMARY',
    ABBREVIATION_ISSUE = 'ABBREVIATION_ISSUE',
}