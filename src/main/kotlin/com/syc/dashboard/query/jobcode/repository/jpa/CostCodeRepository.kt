package com.syc.dashboard.query.jobcode.repository.jpa

import com.syc.dashboard.query.jobcode.entity.CostCode
import org.springframework.data.mongodb.repository.MongoRepository

interface CostCodeRepository : MongoRepository<CostCode, String>
