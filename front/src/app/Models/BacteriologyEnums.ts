export const BacteriologyType = {
  HPV_TEST: { id: -1, name: 'lblHpvTest' },
  SEMEN_CULTURE: { id: -2, name: 'lblSemenCulture' },
  SEMEN_CULTURE_CHLAMYDIA: { id: -3, name: 'lblSemenCultureChlamydia' },
  SEMEN_CULTURE_MYCOPLASMA: { id: -4, name: 'lblSemenCultureMycoplasma' },
  URINE_CULTURE: { id: -5, name: 'cyto-bactériologiquedesurines' },
  VAGINAL_SWAB: { id: -6, name: 'lblVaginalSwab' },
  VAGINAL_SWAB_CHLAMYDIA: { id: -7, name: 'lblVaginalSwabChlamydia' },
  VAGINAL_SWAB_MYCOPLASMA: { id: -8, name: 'lblVaginalSwabMycoplasma' }
} as const;

// Utility to get BacteriologyType by ID
export function getBacteriologyTypeById(id: number): { id: number; name: string } | undefined {
  return Object.values(BacteriologyType).find(type => type.id === id);
}

export const BacteriologyInterpretation = {
  NEGATIVE: { id: 1, name: 'Negative' },
  POSITIVE: { id: 2, name: 'Positive' }
} as const;

// Utility to get BacteriologyInterpretation by ID
export function getBacteriologyInterpretationById(id: number): { id: number; name: string } | undefined {
  return Object.values(BacteriologyInterpretation).find(interpretation => interpretation.id === id);
}

export const Germ = {
  NO_GERM: { id: 0, name: 'No germ' },
  CANDIDA_GLABRATA: { id: 1, name: 'Candida glabrata' },
  STREPTOCOCCUS_AGALACTIAE: { id: 2, name: 'Streptococcus agalactiae' },
  UREAPLASMA_UREALYTICUM: { id: 3, name: 'Uréaplasma Uréalyticum' },
  STAPHYLOCOCCUS_SAPROPHYTICUS: { id: 4, name: 'Staphylococcus saprophyticus' },
  CANDIDA_ALBICANS: { id: 5, name: 'Candida Albicans' },
  ESCHERICHIA_COLI: { id: 6, name: 'Escherichia Coli' },
  GARDNERELLA_VAGINALIS: { id: 7, name: 'Gardnerella vaginalis' },
  CLUE_CELLS: { id: 8, name: 'Clue cells' },
  CANDIDA_SPP: { id: 9, name: 'Candida spp' },
  STAPHYLOCOCCUS_AUREUS: { id: 10, name: 'Staphylococcus aureus' },
  MYCOPLASMA_HOMINIS: { id: 11, name: 'Mycoplasma hominis' },
  CANDIDA_NON_ALBICANS: { id: 12, name: 'Candida non albicans' },
  KLEBSIELLA_PNEUMONIAE: { id: 13, name: 'Klebsiella pneumoniae' },
  TRICHOMONAS_VAGINALIS: { id: 15, name: 'Trichomonas vaginalis' },
  STREPTO_B: { id: 16, name: 'Strepto B' },
  ENTEROCOCCUS_FAECALIS: { id: 17, name: 'Enterococcus faecalis' },
  STAPHYLOCOQUE_COAGULASE_NEGATIVE: { id: 18, name: 'StStaphylocoque coagulase negative' },
  ENTEROCOCCUS_SPP: { id: 19, name: 'Enterococcus spp' },
  ENTEROBACTER_CLOACAE: { id: 20, name: 'Enterobacter cloacae' }
} as const;

// Utility to get Germ by ID
export function getGermById(id: number): { id: number; name: string } | undefined {
  return Object.values(Germ).find(germ => germ.id === id);
}