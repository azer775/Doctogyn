export const SerologyType = {
  ANTI_B2GP1_ANTIBODIES: { id: -1, name: 'Anti-B2GP1 Antibodies' },
  ANTICARDIOLIPIN_ANTIBODIES: { id: -2, name: 'Anticardiolipin Antibodies' },
  ANTINUCLEAR_ANTIBODY: { id: -3, name: 'Antinuclear Antibody (1/)' },
  ANTIPHOSPHOLIPID_ANTIBODIES: { id: -4, name: 'Antiphospholipid Antibodies' },
  CANCER_ANTIGEN_125: { id: -5, name: 'Cancer Antigen 125 (U/mL)' },
  CHLAMYDIA_TRACHOMATIS_SEROLOGY: { id: -6, name: 'Chlamydia Trachomatis Serology' },
  COVID_19_ANTIBODIES: { id: -7, name: 'COVID-19 Antibodies' },
  CYTOMEGALOVIRUS_IGG: { id: -8, name: 'Cytomegalovirus IgG' },
  CYTOMEGALOVIRUS_IGG_AVIDITY: { id: -9, name: 'Cytomegalovirus IgG Avidity' },
  CYTOMEGALOVIRUS_IGM: { id: -10, name: 'Cytomegalovirus IgM' },
  FACTOR_5_LEIDEN_MUTATION_SCREEN: { id: -11, name: 'Factor 5 Leiden Mutation Screen' },
  HEPATITIS_B_ANTIBODY: { id: -12, name: 'Hepatitis B Antibody' },
  HEPATITIS_B_SURFACE_ANTIGEN: { id: -13, name: 'Hepatitis B Surface Antigen' },
  HEPATITIS_C_ANTIBODY: { id: -14, name: 'Hepatitis C Antibody' },
  HERPES_SIMPLEX_SEROLOGY: { id: -15, name: 'Herpes Simplex Serology' },
  HIV_ANTIBODY: { id: -16, name: 'HIV Antibody' },
  IRREGULAR_ANTIBODIES_TEST: { id: -17, name: 'Irregular Antibodies Test (1/)' },
  LUPUS_ANTICOAGULANT: { id: -18, name: 'Lupus Anticoagulant (GPL or MPL)' },
  MYCOPLASMA_SEROLOGY: { id: -19, name: 'Mycoplasma Serology' },
  NEISSERIA_GONORRHEA_SEROLOGY: { id: -20, name: 'Neisseria Gonorrhea Serology' },
  PARVOVIRUS_B19_SEROLOGY_IGG: { id: -21, name: 'Parvovirus B19 Serology IgG' },
  PARVOVIRUS_B19_SEROLOGY_IGM: { id: -22, name: 'Parvovirus B19 Serology IgM' },
  RUBELLA_SEROLOGY_AVIDITY: { id: -23, name: 'Rubella Serology Avidity' },
  RUBELLA_SEROLOGY_IGG: { id: -24, name: 'Rubella Serology IgG' },
  RUBELLA_SEROLOGY_IGM: { id: -25, name: 'Rubella Serology IgM' },
  SYPHILIS_SEROLOGY: { id: -26, name: 'Syphilis Serology' },
  TOXOPLASMA_PCR: { id: -27, name: 'Toxoplasma PCR' },
  TOXOPLASMA_SEROLOGY_AVIDITY: { id: -28, name: 'Toxoplasma Serology Avidity' },
  TOXOPLASMA_SEROLOGY_IGG: { id: -29, name: 'Toxoplasma Serology IgG' },
  TOXOPLASMA_SEROLOGY_IGM: { id: -30, name: 'Toxoplasma Serology IgM' },
  TRICHOMONAS_VAGINALIS_SEROLOGY: { id: -31, name: 'Trichomonas Vaginalis Serology' },
  CA_19_9: { id: -32, name: 'CA 19-9 (mU/mL or μg/mL)' },
  ACE: { id: -33, name: 'ACE (ng/mL)' },
  HCG: { id: -34, name: 'HCG' },
  AC_DNA_NATIF: { id: -35, name: 'AC DNA Natif' }
} as const;

// Utility to get SerologyType by ID
export function getSerologyTypeById(id: number): typeof SerologyType[keyof typeof SerologyType] | undefined {
  return Object.values(SerologyType).find(type => type.id === id);
}

export const SerologyInterpretation = {
  NEGATIVE: { id: 1, name: 'Negative' },
  POSITIVE: { id: 2, name: 'Positive' },
  EQUIVOQUAL: { id: 3, name: 'Equivoqual' }
} as const;

// Utility to get SerologyInterpretation by ID
export function getSerologyInterpretationById(id: number): typeof SerologyInterpretation[keyof typeof SerologyInterpretation] | undefined {
  return Object.values(SerologyInterpretation).find(interpretation => interpretation.id === id);
}