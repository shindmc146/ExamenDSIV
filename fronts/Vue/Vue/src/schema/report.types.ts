export interface Report {
  id: number
  companyName: string
  passengerName: string
  pictureURL: string | null
  type: 'COMPLAINT' | 'LOSTITEM' | 'SUGGESTION'
  subject: string
  description: string
  tripDate: string
  isUrgent: boolean
  createdAt: string
}

export interface ReportPayload {
  companyName: string
  passengerName: string
  type: 'COMPLAINT' | 'LOSTITEM' | 'SUGGESTION'
  subject: string
  description: string
  tripDate: string
  isUrgent: boolean
  pictureURL: string | null
}
